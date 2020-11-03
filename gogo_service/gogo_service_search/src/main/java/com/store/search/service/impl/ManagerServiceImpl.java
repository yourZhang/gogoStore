
package com.store.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.store.feign.SkuFeign;
import com.store.pojo.Sku;
import com.store.search.pojo.SkuInfo;
import com.store.search.repository.SkuInfoMapper;
import com.store.search.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ManagerServiceImpl implements ManagerService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private SkuInfoMapper skuInfoMapper;

    /**
     * 功能描述: <br>
     * 〈导入历史数据〉
     *
     * @Param: []
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/2 20:21
     */
    @Override
    public void importAll() {

        //1.调用skufeign-->skuList
        List<Sku> skuList = skuFeign.findAllList();
        saveSkuList(skuList);
    }

    /**
     * 功能描述: <br>
     * 〈保存skuList数据〉
     *
     * @Param: [skuList]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/2 20:22
     */
    private void saveSkuList(List<Sku> skuList) {
        logger.info("查询完毕数据量： {}", skuList.size());
        //2.使用json将skuList转换skuInfoList
        List<SkuInfo> skuInfoList = JSON.parseArray(JSON.toJSONString(skuList), SkuInfo.class);
        //3.specMap特殊处理--->遍历skuInfoList 将spec转换为specMap
        for (SkuInfo skuInfo : skuInfoList) {
            String spec = skuInfo.getSpec();
            Map specMap = JSON.parseObject(spec, Map.class);
            skuInfo.setSpecMap(specMap);
        }
        logger.info("json转换完毕数据量： {}", skuInfoList.size());
        //4.保存
        long begin = System.currentTimeMillis();
        skuInfoMapper.saveAll(skuInfoList);
        long end = System.currentTimeMillis();
        logger.info("保存完毕： {}", (end - begin));
    }

    /**
     * 功能描述: <br>
     * 〈保存skuList数据〉
     *
     * @Param: [spuId]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/2 20:22
     */
    @Override
    public void importBySpuId(String spuId) {
        List<Sku> skuList = skuFeign.findBySpuId(spuId);
        saveSkuList(skuList);
    }

    /**
     * 功能描述: <br>
     * 〈下架es商品〉
     *
     * @Param: [spuId]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/3 11:43
     */
    @Override
    public void downBySpuId(String spuId) {
        final List<Sku> bySpuId = skuFeign.findBySpuId(spuId);
        bySpuId.forEach((values) -> {
            skuInfoMapper.deleteById(Long.parseLong(values.getId()));
        });
        logger.info("下架成功成功:{}", spuId);
    }
}
