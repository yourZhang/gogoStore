package com.store.page.service.impl;

import com.alibaba.fastjson.JSON;
import com.store.feign.CategoryFeign;
import com.store.feign.SkuFeign;
import com.store.feign.SpuFeign;
import com.store.page.service.PageService;
import com.store.pojo.Sku;
import com.store.pojo.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: gogo-store
 * @description:
 * @author: xiaozhang6666
 * @create: 2020-11-05 15:40
 **/
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private TemplateEngine template;
    @Value("${pagepath}")
    private String pagepath;
    @Autowired
    private SpuFeign spuFeign;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private CategoryFeign categoryFeign;

    @Override
    public void createHtml(String spuId) {
        //1.创建内容对象
        Context context = new Context();
        //2.设置数据
        Map<String, Object> pageData = builderPageData(spuId);
        context.setVariables(pageData);
        //3.创建输出流对象
        Writer writer = null;
        try {
            //指定输出地址
            writer = new PrintWriter(pagepath + "/" + spuId + ".html");
            template.process("item", context, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /*
        获取详情页需要的数据
     */
    private Map<String, Object> builderPageData(String spuId) {
        Map<String, Object> pageData = new HashMap<>();
        //1.spu
        Spu spu = spuFeign.findByIds(spuId);
        pageData.put("spu", spu);
        //2.skuList
        List<Sku> skuList = skuFeign.findBySpuId(spuId);
        pageData.put("skuList", skuList);
        //3.spec-->规格来源于spu
        String specItems = spu.getSpecItems();
        Map map = JSON.parseObject(specItems, Map.class);
        pageData.put("specificationList", map);
        //4.图片列表--》来源于spu 多个地址以逗号分隔并且前段需要的是数组格式
        String images = spu.getImages();
        String[] split = images.split(",");
        pageData.put("imageList", split);
        //5.分类---》三级分类全部都需要从spu中获取三级分类的id再次查询
        pageData.put("category1", categoryFeign.findById(spu.getCategory1Id()));
        pageData.put("category2", categoryFeign.findById(spu.getCategory2Id()));
        pageData.put("category3", categoryFeign.findById(spu.getCategory3Id()));
        return pageData;
    }
}
