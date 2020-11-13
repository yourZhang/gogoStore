package com.store.pay.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("pay")
@RequestMapping("/wxpay")
public interface PayFeign {

    /**
     * 功能描述: <br>
     * 〈预支付接口〉
     *
     * @Param: []
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @Author: xiaozhang666
     * @Date: 2020/11/12 16:20
     */
    @GetMapping("/nativePay")
    public Map<String, String> nativePay();

    /**
     * 功能描述: <br>
     * 〈调用微信查询接口查询是否支付成功〉
     *
     * @Param: [orderId]
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @Author: xiaozhang666
     * @Date: 2020/11/13 17:10
     */
    @GetMapping("/query/{orderId}")
    public Map<String, String> queryPay(@PathVariable(value = "orderId") String orderId);

    /**
     * 功能描述: <br>
     * 〈调用微信关闭支付通道接口, 关闭支付〉
     *
     * @Param: [orderId]
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @Author: xiaozhang666
     * @Date: 2020/11/13 17:10
     */
    @GetMapping("/close/{orderId}")
    public Map<String, String> closePay(@PathVariable(value = "orderId") String orderId);
}
