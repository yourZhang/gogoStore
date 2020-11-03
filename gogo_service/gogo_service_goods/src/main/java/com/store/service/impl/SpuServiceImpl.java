package com.store.service.impl;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.store.mapper.*;
import com.store.pojo.*;
import com.store.service.SpuService;
import com.store.util.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public void addGoods(Goods goods) {
        //1.保存spu
        Spu spu = goods.getSpu();
        spu.setId(idWorker.nextId() + "");
        spuMapper.insertSelective(spu);
        //2.保存skuList
        saveSkuList(goods);
    }

    @Override
    public Goods findGoods(String spuId) {
        Goods goods = new Goods();
        //1.查询spu
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        goods.setSpu(spu);
        //2.查询skuList
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        goods.setSkuList(skuList);
        return goods;
    }

    @Override
    public void updateGoods(Goods goods) {
        Spu spu = goods.getSpu();
        //1.更新spu
        spuMapper.updateByPrimaryKeySelective(spu);
        //2.更新skuList
        //1.根据spuId删除
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        skuMapper.delete(sku);
        //2.调用公共的保存skuList方法
        saveSkuList(goods);
    }

    @Override
    public void auditGoods(String spuId) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setStatus("1"); //审核通过
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void upGoods(String spuId) {
        //1.判断商品是否审核通过
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //2.如果通过则上架
        if (spu.getStatus().equals("1")) {
            //此处为了提高效率所以采取new 对象的方式
            //spu.setIsMarketable("1"); //上架状态
            Spu spu1 = new Spu();
            spu1.setId(spuId);
            spu1.setIsMarketable("1");
            spuMapper.updateByPrimaryKeySelective(spu1);
             /*
                上架成功发送spuId至mq
             */
            rabbitTemplate.convertAndSend("goods_up_exchange", "", spuId);
        } else {
            //3.如果未通过则抛出异常----》运行时异常---》未审核商品不得上架
            throw new RuntimeException("未审核商品不得上架....");
        }
    }

    @Override
    public void down(String spuId) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setIsMarketable("0"); //下架状态
        spuMapper.updateByPrimaryKeySelective(spu);
        //下架后根据id删除es中的数据
        rabbitTemplate.convertAndSend("goods_down_exchange", "", spuId);
    }

    @Override
    public void deleteLogic(String spuId) {
        //1.查询spu对象
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //2.判断商品的上下架状态
        if (spu.getIsMarketable().equals("1")) {
            //3.如果是上架抛出异常
            throw new RuntimeException("请先将商品下架后再删除.....");
        }
        //4.下架状态---》删除  is_delete=1 status=0
        Spu spu1 = new Spu();
        spu1.setId(spuId);
        spu1.setIsDelete("1"); //删除
        spu1.setStatus("0"); //未审核
        spuMapper.updateByPrimaryKeySelective(spu1);
    }

    @Override
    public void restore(String spuId) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setIsDelete("0"); //还原
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void deleteGoods(String spuId) {
        spuMapper.deleteByPrimaryKey(spuId);
    }

    private void saveSkuList(Goods goods) {
        Spu spu = goods.getSpu();
        List<Sku> skuList = goods.getSkuList();
        for (Sku sku : skuList) {
            //1.id设置
            sku.setId(idWorker.nextId() + "");
            //2.name
            /*
                ""
                spec: {'颜色': '梵高星空典藏版', '版本': '8GB+128GB'}
                skuName = spuName + spec
                fastjson--->map
             */
            String spec = sku.getSpec();
            if (StringUtils.isEmpty(spec)) {//此处是为了防止json转换异常
                spec = "{}";
            }
            Map<String, String> specMap = JSON.parseObject(spec, Map.class);
            String skuName = spu.getName();
            for (Map.Entry<String, String> entry : specMap.entrySet()) {
                String value = entry.getValue();
                skuName = skuName + " " + value;
            }
            logger.info("拼接完毕的specName : {}", skuName);
            sku.setName(skuName);
            //3.创建时间和更新时间
            sku.setCreateTime(new Date());
            sku.setUpdateTime(new Date());
            //4.spuId
            sku.setSpuId(spu.getId());
            //5.分类id和分类名称
            Integer category3Id = spu.getCategory3Id(); //此处sku中保存的是三级分类id和名称
            Category category = categoryMapper.selectByPrimaryKey(category3Id);
            sku.setCategoryName(category.getName());
            sku.setCategoryId(category3Id);
            //6.品牌名称
            Integer brandId = spu.getBrandId();
            Brand brand = brandMapper.selectByPrimaryKey(brandId);
            sku.setBrandName(brand.getName());

            //隐藏需求：品牌分类关联关系表维护
            /*
                1.先去查询是否有
                2.如果存在则不操作
                3.如果不存在则新增
             */
            CategoryBrand categoryBrand = new CategoryBrand();
            categoryBrand.setBrandId(brandId);
            categoryBrand.setCategoryId(category3Id);
            int count = categoryBrandMapper.selectCount(categoryBrand);
            if (count == 0) {
                categoryBrandMapper.insertSelective(categoryBrand);
            }
            skuMapper.insertSelective(sku);
        }
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Spu findById(String id) {
        return spuMapper.selectByPrimaryKey(id);
    }


    /**
     * 增加
     *
     * @param spu
     */
    @Override
    public void add(Spu spu) {
        spuMapper.insert(spu);
    }


    /**
     * 修改
     *
     * @param spu
     */
    @Override
    public void update(Spu spu) {
        spuMapper.updateByPrimaryKey(spu);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        spuMapper.deleteByPrimaryKey(id);
    }


    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    @Override
    public List<Spu> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return spuMapper.selectByExample(example);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Spu> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        return (Page<Spu>) spuMapper.selectAll();
    }

    /**
     * 条件+分页查询
     *
     * @param searchMap 查询条件
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @Override
    public Page<Spu> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        return (Page<Spu>) spuMapper.selectByExample(example);
    }

    /**
     * 构建查询对象
     *
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 主键
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 货号
            if (searchMap.get("sn") != null && !"".equals(searchMap.get("sn"))) {
                criteria.andEqualTo("sn", searchMap.get("sn"));
            }
            // SPU名
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 副标题
            if (searchMap.get("caption") != null && !"".equals(searchMap.get("caption"))) {
                criteria.andLike("caption", "%" + searchMap.get("caption") + "%");
            }
            // 图片
            if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                criteria.andLike("image", "%" + searchMap.get("image") + "%");
            }
            // 图片列表
            if (searchMap.get("images") != null && !"".equals(searchMap.get("images"))) {
                criteria.andLike("images", "%" + searchMap.get("images") + "%");
            }
            // 售后服务
            if (searchMap.get("saleService") != null && !"".equals(searchMap.get("saleService"))) {
                criteria.andLike("saleService", "%" + searchMap.get("saleService") + "%");
            }
            // 介绍
            if (searchMap.get("introduction") != null && !"".equals(searchMap.get("introduction"))) {
                criteria.andLike("introduction", "%" + searchMap.get("introduction") + "%");
            }
            // 规格列表
            if (searchMap.get("specItems") != null && !"".equals(searchMap.get("specItems"))) {
                criteria.andLike("specItems", "%" + searchMap.get("specItems") + "%");
            }
            // 参数列表
            if (searchMap.get("paraItems") != null && !"".equals(searchMap.get("paraItems"))) {
                criteria.andLike("paraItems", "%" + searchMap.get("paraItems") + "%");
            }
            // 是否上架
            if (searchMap.get("isMarketable") != null && !"".equals(searchMap.get("isMarketable"))) {
                criteria.andEqualTo("isMarketable", searchMap.get("isMarketable"));
            }
            // 是否启用规格
            if (searchMap.get("isEnableSpec") != null && !"".equals(searchMap.get("isEnableSpec"))) {
                criteria.andEqualTo("isEnableSpec", searchMap.get("isEnableSpec"));
            }
            // 是否删除
            if (searchMap.get("isDelete") != null && !"".equals(searchMap.get("isDelete"))) {
                criteria.andEqualTo("isDelete", searchMap.get("isDelete"));
            }
            // 审核状态
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andEqualTo("status", searchMap.get("status"));
            }

            // 品牌ID
            if (searchMap.get("brandId") != null) {
                criteria.andEqualTo("brandId", searchMap.get("brandId"));
            }
            // 一级分类
            if (searchMap.get("category1Id") != null) {
                criteria.andEqualTo("category1Id", searchMap.get("category1Id"));
            }
            // 二级分类
            if (searchMap.get("category2Id") != null) {
                criteria.andEqualTo("category2Id", searchMap.get("category2Id"));
            }
            // 三级分类
            if (searchMap.get("category3Id") != null) {
                criteria.andEqualTo("category3Id", searchMap.get("category3Id"));
            }
            // 模板ID
            if (searchMap.get("templateId") != null) {
                criteria.andEqualTo("templateId", searchMap.get("templateId"));
            }
            // 运费模板id
            if (searchMap.get("freightId") != null) {
                criteria.andEqualTo("freightId", searchMap.get("freightId"));
            }
            // 销量
            if (searchMap.get("saleNum") != null) {
                criteria.andEqualTo("saleNum", searchMap.get("saleNum"));
            }
            // 评论数
            if (searchMap.get("commentNum") != null) {
                criteria.andEqualTo("commentNum", searchMap.get("commentNum"));
            }

        }
        return example;
    }

}
