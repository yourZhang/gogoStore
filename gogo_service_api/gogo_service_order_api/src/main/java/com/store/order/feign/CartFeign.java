package com.store.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 *
 */
@FeignClient(name = "order")
@RequestMapping("/cart")
public interface CartFeign {

    /**
     * 功能描述: <br>
     * 〈查询购物车数据返回〉
     *
     * @Param: []
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: xiaozhang666
     * @Date: 2020/11/11 15:04
     */
    @GetMapping("/list")
    public Map<String, Object> list();

    /**
     * 添加修改购物车中的购物项
     *
     * @param skuId 库存id
     * @param num   购买数量
     */
    @GetMapping("/add/{skuId}/{num}")
    public void addAndUpdate(@PathVariable(value = "skuId") String skuId, @PathVariable(value = "num") Integer num);
}
