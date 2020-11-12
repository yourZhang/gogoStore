package com.store.pay.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
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
}
