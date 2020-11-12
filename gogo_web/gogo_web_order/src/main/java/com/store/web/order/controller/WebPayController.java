package com.store.web.order.controller;


import com.store.pay.feign.PayFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 支付controller
 */
@Controller
@RequestMapping("/wpay")
public class WebPayController {

    @Autowired
    private PayFeign payFeign;

    /**
     * 预支付
     * @return
     */
    @GetMapping("/nativePay")
    public String nativePay(Model model) {
        //1. 通过feign远程调用, 支付业务微服务中的预支付接口
        Map<String, String> resultMap = payFeign.nativePay();
        //2. 将返回的支付链接等数据, 放入model中
        //订单号
        model.addAttribute("orderId", resultMap.get("orderId"));
        //支付总金额
        model.addAttribute("payMoney", Double.parseDouble(resultMap.get("payMoney")));
        //支付链接
        model.addAttribute("code_url", resultMap.get("code_url"));
        //3. 返回到支付页面
        return "wxpay";
    }
}
