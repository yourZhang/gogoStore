package com.store.pay.service;

import java.util.Map;

/**
 *
 */
public interface PayService {

    /**
     * 预支付接口, 调用微信统一下单接口, 返回支付链接
     * @param userName  当前登录用户的用户名
     * @return
     */
    public Map<String, String> nativePay(String userName);
    /**
     * 调用微信查询接口
     * @param orderId
     * @return
     */
    public Map<String, String> queryPay(String orderId);

    /**
     * 调用微信关闭支付通道接口
     * @param orderId
     * @return
     */
    public Map<String, String> closePay(String orderId);
}
