package com.store.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.store.entity.Page;
import com.store.search.pojo.SkuInfo;
import com.store.search.service.SearchService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: gogo-store
 * @description:
 * @author: xiaozhang6666
 * @create: 2020-11-04 15:49
 **/
@Service
public class SearchServiceImpl implements SearchService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ElasticsearchTemplate template;

    /**
     * 功能描述: <br>
     * 〈简单搜索功能--商品〉
     *
     * @Param: [searchMap]
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: xiaozhang666
     * @Date: 2020/11/4 15:52
     */
    @Override
    public Map<String, Object> getList(Map<String, String> searchMap) {
        //设置返回对象
        Map<String, Object> resultMap = new HashMap<>();
        //用于设置查询条件对象  构建查询对象  用于基础查询，条件查询，模糊查询，精确查询，不持支排序分组聚合
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        /*
         * 需求一
         *关键字模糊查询   --  查询name字段
         * must  filter  方法
         * */
        String keywords = searchMap.get("keywords");
        if (StringUtils.isNotEmpty(keywords)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("name", keywords));
        }

        /*
            需求五： 根据品牌精确查询   brandName
             使用 boolQueryBuilder
         */
        String brandName = searchMap.get("brand");
        if (StringUtils.isNotEmpty(brandName)) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("brandName", brandName));
        }

        /*
            需求六： 根据分类精确查询   categoryName
            使用 boolQueryBuilder
         */
        String categoryName = searchMap.get("categoryName");
        if (StringUtils.isNotEmpty(categoryName)) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("categoryName", categoryName));
        }

        /*
         *根据规格精确查询
         * 规格接收到前端的参数为  --  键值对
         *                特点：
                    前段可以传送多个规格条件：
                        spec_颜色：红色
                        spec_版本： 4g+128g
                    后台es实际存储
                        specMap.颜色： 红色、蓝色、黑色
                        specMap.版本： 4g+128g 4g+64g
                    此处需要注意使用的是精确查询需要字段是keyword类型
         *
         * */
        //先在map中找到  相应的参数
        //遍历
        searchMap.forEach((key, value) -> {
            if (key.startsWith("spec_")) {
                //因为 es中的字段名与前端传递的字段名不符合，所以要进行替换
                String specKey = key.replaceAll("spec_", "specMap.");
                String specKeyword = specKey + ".keyword";
                logger.info("拼接完毕的spec字段： {}", specKeyword);
                boolQueryBuilder.filter(QueryBuilders.termQuery(specKeyword, value));
            }
        });

        /*需求 八
         * 根据价格范围查询
         *   price 100 - 200
         *   价格范围
         * */
        String price = searchMap.get("price");
        if (StringUtils.isNotEmpty(price)) {
            String[] split = price.split("-");
            if (split.length == 2) {
                String beginPrice = split[0];
                String endPrice = split[1];
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(beginPrice).lte(endPrice));
            }
        }
        //原生查询对象-->用于封装查询条件 boolQueryBuilder  +  查询结果的处理对象(分页+排序+高亮)
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //将以上  --  查询封装
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

        /*
        * 需求二
        * 品牌列表查询分组聚合--->设置查询条件  字段名   brandName
                1.组名
                2.分组字段
        * */
        String brandGroup = "brandGroup";
        //此处 先设置条件再 规定字段
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms(brandGroup).field("brandName");
        nativeSearchQueryBuilder.addAggregation(brandAgg);

        /*
            需求三：分类列表分组聚合--->设置分组条件
                1.组名
                2.分组字段
         */
        String cateGroup = "cateGroup";
        TermsAggregationBuilder categoryGroup = AggregationBuilders.terms(cateGroup).field("categoryName");
        nativeSearchQueryBuilder.addAggregation(categoryGroup);

        /*
            需求四： 规格列表分组聚合--->设置条件
                组名
                分组字段
         */
        String specGroup = "specGroup";
        //"spec.keyword" 由于再索引库中没有指定字段类型，所以默认为text，text不能使用聚合，所以临时转换为 keyword
        TermsAggregationBuilder specAgg = AggregationBuilders.terms(specGroup).field("spec.keyword").size(10000);
        nativeSearchQueryBuilder.addAggregation(specAgg);


        /*
         * 需求 九
         *  排序查询
         *   设置排序字段
         *   设置排序规则
         * */
        String sortField = searchMap.get("sortField");
        String sortRule = searchMap.get("sortRule");
        if (StringUtils.isNotEmpty(sortField) && StringUtils.isNotEmpty(sortRule)) {
            if ("desc".equalsIgnoreCase(sortRule)) {
                FieldSortBuilder sortBuilder = SortBuilders.fieldSort(sortField).order(SortOrder.DESC);
                nativeSearchQueryBuilder.withSort(sortBuilder);
            } else {
                FieldSortBuilder sortBuilder = SortBuilders.fieldSort(sortField).order(SortOrder.ASC);
                nativeSearchQueryBuilder.withSort(sortBuilder);
            }
        }

        /*
         * 需求  十
         * 分页查询
         *   需要设置  pageNum 和  pageSize
         * */
        //common 工具类中
        Integer pageNum = Page.pageNum;
        Integer pageSize = Page.pageSize;
        String pageNumStr = searchMap.get("pageNum");
        if (StringUtils.isNotEmpty(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        String pageSizeStr = searchMap.get("pageSize");
        if (StringUtils.isNotEmpty(pageSizeStr)) {
            pageSize = Integer.valueOf(pageSizeStr);
        }
        //es中提供的分页对象  PageRequest
        PageRequest pageable = PageRequest.of(pageNum - 1, pageSize);
        nativeSearchQueryBuilder.withPageable(pageable);


        /*
         * 需求 十一
         *   前置标签
         *   后置标签
         *   高亮字段
         * */
        //api的高亮对象
        HighlightBuilder.Field highlightField = new HighlightBuilder
                .Field("name")
                .preTags("<span color='red'>")
                .postTags("</span>");
        nativeSearchQueryBuilder.withHighlightFields(highlightField);

        //不要忘记  nativeSearchQueryBuilder   传入各种查询对象
        //使用 template 发送请求
        AggregatedPage<SkuInfo> aggregatedPage = template.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class, new SearchResultMapper() {
            @Override
            /**
             * 功能描述: <br>
             * 〈response 返回结果
             *
             * 〉
             * @Param: [response, clazz, pageable]
             * @return: org.springframework.data.elasticsearch.core.aggregation.AggregatedPage<T>
             * @Author: xiaozhang666
             * @Date: 2020/11/4 17:32
             */
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                //存放查询结果列表
                List<T> list = new ArrayList<>();
                SearchHits searchHits = response.getHits();
                if (null != searchHits) {
                    SearchHit[] hits = searchHits.getHits();
                    for (SearchHit hit : hits) {
                        String skuInfoJson = hit.getSourceAsString();
                        SkuInfo skuInfo = JSON.parseObject(skuInfoJson, SkuInfo.class);

                         /*
                            需求十一： 高亮结果处理
                         */
                        String name = hit.getHighlightFields().get("name").getFragments()[0].toString();
                        skuInfo.setName(name);
                        list.add((T) skuInfo);
                    }
                    /*
                        * 第一个参数： 查询结果
                        第二个参数： 分页对象
                        第三个参数： 查询总记录数
                        第四个参数： 分组聚合结果
                     */
                    return new AggregatedPageImpl<T>(list, pageable, searchHits.getTotalHits(), response.getAggregations());
                }
                return new AggregatedPageImpl<T>(list);
            }
        });

         /*
         根据需求进行处理
            需求二： 品牌列表   查询分组聚合--->结果解析
                1.组名
         */
        //从结果中取出聚合，进行封装
        List<String> brandList = new ArrayList<>();
        //因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms brandStringTrems = (StringTerms) aggregatedPage.getAggregation(brandGroup);
        //聚合桶  -》 获取桶
        List<StringTerms.Bucket> brandBuckets = brandStringTrems.getBuckets();
        for (StringTerms.Bucket bucket : brandBuckets) {
            String key = bucket.getKeyAsString();
            brandList.add(key);
        }

        /*
            需求三：分类列表   分组聚合--->结果解析
             组名
         */
        List<String> cateList = new ArrayList<>();
        StringTerms cateStringTerms = (StringTerms) aggregatedPage.getAggregation(cateGroup);
        List<StringTerms.Bucket> cateStringTermsBuckets = cateStringTerms.getBuckets();
        for (StringTerms.Bucket bucket : cateStringTermsBuckets) {
            String key = bucket.getKeyAsString();
            cateList.add(key);
        }

        /*
         * 需求四： 规格列表分组聚合--->结果解析
         *         规格列表
         *现有结果不符合期望结果
         * 如下
         * */
                /*
                现有结果
              "specList": [
              实际：
                "{'颜色': '蓝色', '版本': '6GB+128GB'}",
                "{'颜色': '蓝色', '版本': '4GB+64GB'}",
                "{'颜色': '蓝色', '版本': '6GB+64GB'}",
                "{'颜色': '蓝色'}",

                "{'颜色': '黑色', '版本': '6GB+128GB'}",
                "{'颜色': '黑色', '版本': '4GB+64GB'}",
                "{'颜色': '黑色', '版本': '6GB+64GB'}",
                "{'颜色': '黑色'}",

                "{'颜色': '金色', '版本': '4GB+64GB'}",
                "{'颜色': '粉色', '版本': '6GB+128GB'}"
            期望结果：
			    颜色： 蓝色 黑色 金色 粉色
			    版本：6GB+128GB 4GB+64GB 6GB+64GB
            解决方案
			    Map<String, Set<String>>
        ],
         */
        Map<String, Set<String>> specMap = new HashMap<>();
        StringTerms specStringTrems = (StringTerms) aggregatedPage.getAggregation(specGroup);
        List<StringTerms.Bucket> specStringTremsBuckets = specStringTrems.getBuckets();
        specStringTremsBuckets.forEach((value) -> {
            final String keyAsString = value.getKeyAsString();
            //1.json转map
            Map<String, String> map = JSON.parseObject(keyAsString, Map.class);
            //2.遍历map
            map.forEach((key, chValue) -> {
                if (specMap.containsKey(key)) {
                    Set<String> oldSpecSet = specMap.get(key);
                    oldSpecSet.add(chValue);
                    specMap.put(key, oldSpecSet);
                } else {
                    Set<String> newSpecSet = new HashSet<>();
                    newSpecSet.add(chValue);
                    specMap.put(key, newSpecSet);
                }
            });
        });
        //处理查询结果
        List<SkuInfo> skuInfoList = aggregatedPage.getContent(); //结果列表
        long totalElements = aggregatedPage.getTotalElements(); //总记录数
        int totalPages = aggregatedPage.getTotalPages(); //总页数
        resultMap.put("rows", skuInfoList);
        resultMap.put("total", totalElements);
        resultMap.put("totalPage", totalPages);
        resultMap.put("brandList", brandList); //品牌列表
        resultMap.put("cateList", cateList); //分类列表
        resultMap.put("specList", specMap); //规格列表
        return resultMap;
    }
}
