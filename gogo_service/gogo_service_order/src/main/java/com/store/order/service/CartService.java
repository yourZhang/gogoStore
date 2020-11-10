package com.store.order.service;

import java.util.Map;

/**
 *
 */
public interface CartService {

    /**
     * 根据消费者用户名获取购物车数据
     * @param userName  消费者用户名
     * @return
     */
    public Map<String, Object> list(String userName);

    /**
     * 添加或修改购物车中的购物项
     * @param skuId     购买商品的库存id
     * @param num       购买数量(可能是正数可能是负数)
     * @param userName  当前消费者用户名
     */
    public void addAndUpdate(String skuId, Integer num, String userName);
}
