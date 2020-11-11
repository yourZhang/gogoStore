package com.store.order.service;

import java.util.Map;

/**
 *
 */
public interface CartService {

    /**
     * 根据消费者用户名获取购物车数据
     *
     * @param userName 消费者用户名
     * @return
     */
    public Map<String, Object> list(String userName);

    /**
     * 添加或修改购物车中的购物项
     *
     * @param skuId    购买商品的库存id
     * @param num      购买数量(可能是正数可能是负数)
     * @param userName 当前消费者用户名
     */
    public void addAndUpdate(String skuId, Integer num, String userName);

    /**
     * 删除购物车中的购物项
     *
     * @param skuId
     */
    public void delete(String skuId, String userName);

    /**
     * 功能描述: <br>
     * 〈更新购物车列表复选框状态〉
     *
     * @Param: [skuId, checked, userName]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/11 19:48
     */
    public void updateChecked(String skuId, boolean checked, String userName);

    public Map<String, Object> buyList(String userName);

}
