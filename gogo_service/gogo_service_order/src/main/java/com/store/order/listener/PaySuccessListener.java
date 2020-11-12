package com.store.order.listener;


import com.alibaba.fastjson.JSON;
import com.store.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 支付成功业务处理监听器
 */
@Component
@RabbitListener(queues = "order_pay")
public class PaySuccessListener {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void messageHandler(String paySuccessJsonStr) {
        Map<String, String> paramMap = JSON.parseObject(paySuccessJsonStr, Map.class);
        //1. 获取支付成功订单号
        String out_trade_no = paramMap.get("out_trade_no");
        //2. 获取支付成功交易流水号
        String transaction_id = paramMap.get("transaction_id");
        //3. 调用service, 完成支付成功业务处理
        orderService.paySuccessOrder(transaction_id, out_trade_no);
    }
}
