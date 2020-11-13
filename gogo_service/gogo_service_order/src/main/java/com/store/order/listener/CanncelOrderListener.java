package com.store.order.listener;


import com.store.order.config.RabbitMQConfig;
import com.store.order.pojo.Order;
import com.store.order.service.OrderService;
import com.store.pay.feign.PayFeign;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 监听死信队列, 获取所有超时的订单号(所有订单最终都会超时)
 * 在这里对于所有订单都会进行校验, 看是否真正支付成功并且做正常支付业务流程处理
 */
@Component
@RabbitListener(queues = RabbitMQConfig.CANCEL_ORDER_QUEUE)
public class CanncelOrderListener {

    @Autowired
    private PayFeign payFeign;

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void messageHandler(String orderId) {
        //1. 调用微信查询接口, 查询是否支付成功
        Map<String, String> wxReslutMap = payFeign.queryPay(orderId);

        //2. 如果微信返回支付成功
        if ("SUCCESS".equals(wxReslutMap.get("trade_state"))) {
            //3. 根据订单id到数据库订单表查询订单状态
            Order order = orderService.findById(orderId);
            //4. 判断数据库中订单的状态如果为未支付, 做支付成功补偿处理
            if ("0".equals(order.getPayStatus())) {
                orderService.paySuccessOrder(wxReslutMap.get("transaction_id"), orderId);
            }
        }
        //5. 如果微信返回未支付
        if ("NOTPAY".equals(wxReslutMap.get("trade_state"))) {
            //6. 调用service做订单超时处理业务
            orderService.cancelPayOrder(orderId);
        }
    }
}
