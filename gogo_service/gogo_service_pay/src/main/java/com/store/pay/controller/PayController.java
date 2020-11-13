package com.store.pay.controller;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import com.store.pay.config.TokenDecode;
import com.store.pay.service.PayService;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付接口controller
 */
@RestController
@RequestMapping("/wxpay")
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private TokenDecode tokenDecode;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 预支付接口
     *
     * @return
     */
    @GetMapping("/nativePay")
    public Map<String, String> nativePay() {
        //获取当前登录用户的用户名
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");

        Map<String, String> resultMap = payService.nativePay(username);
        return resultMap;
    }

    /**
     * 回调接口, 接收内网穿透服务器发来的支付成功消息
     *
     * @return
     */
    @RequestMapping("/notify")
    public String payNotify(HttpServletRequest request) throws Exception {
        //1. 从请求中接收输入流
        ServletInputStream inputStream = request.getInputStream();
        //2. 将流转换成字符串
        String str = IOUtils.toString(inputStream, "utf-8");
        //System.out.println("======" + str);
        //3. 将xml格式字符串转换成Map
        Map<String, String> wxMap = WXPayUtil.xmlToMap(str);

        //订单号
        String out_trade_no = wxMap.get("out_trade_no");
        //微信交易流水号, 后期对账使用, 支付成功才有这个号
        String transaction_id = wxMap.get("transaction_id");

        /**
         * 将订单id和交易流水号封装成map
         * 将支付成功订单id和交易流水号发送到rabbitMq的order_pay支付成功队列中
         */
        Map<String, String> paySuccessMap = new HashMap<>();
        paySuccessMap.put("out_trade_no", out_trade_no);
        paySuccessMap.put("transaction_id", transaction_id);
        rabbitTemplate.convertAndSend("", "order_pay", JSON.toJSONString(paySuccessMap));
        /**
         * 将订单id发送到rabbitmq的paynotify交换器中
         * paynotify交换器会将支付成功的订单号推送到消费者页面
         * 使用的是websocket多链路双向通讯技术
         */
        rabbitTemplate.convertAndSend("paynotify", "", out_trade_no);
        /**
         * 封装返回给微信服务器的数据返回, 告诉微信, 别再给我回调数据了, 我收到了
         */
        Map<String, String> wxResultMap = new HashMap<>();
        wxResultMap.put("return_code", "SUCCESS");
        wxResultMap.put("return_msg", "OK");
        return WXPayUtil.mapToXml(wxResultMap);
    }

    /**
     * 功能描述: <br>
     * 〈调用微信查询接口查询是否支付成功〉
     *
     * @Param: [orderId]
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @Author: xiaozhang666
     * @Date: 2020/11/13 16:19
     */
    @GetMapping("/query/{orderId}")
    public Map<String, String> queryPay(@PathVariable(value = "orderId") String orderId) {
        Map<String, String> resultMap = payService.queryPay(orderId);
        return resultMap;
    }

    /**
     * 功能描述: <br>
     * 〈调用微信关闭支付通道接口, 关闭支付〉
     *
     * @Param: [orderId]
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @Author: xiaozhang666
     * @Date: 2020/11/13 16:19
     */
    @GetMapping("/close/{orderId}")
    public Map<String, String> closePay(@PathVariable(value = "orderId") String orderId) {
        Map<String, String> resultMap = payService.closePay(orderId);
        return resultMap;
    }
}
