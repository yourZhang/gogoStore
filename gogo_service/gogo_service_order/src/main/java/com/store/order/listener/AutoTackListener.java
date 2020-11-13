package com.store.order.listener;


import com.store.order.dao.OrderConfigMapper;
import com.store.order.dao.OrderMapper;
import com.store.order.pojo.Order;
import com.store.order.pojo.OrderConfig;
import com.store.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDate;
import java.util.List;

/**
 * 自动收货监听器
 * 从队列中接收到一个占位符, 自动收货业务流程就执行一遍
 */
@Component
@RabbitListener(queues = "order_tack")
public class AutoTackListener {

    @Autowired
    private OrderConfigMapper orderConfigMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void messageHandler(String msg) {
        //1. 获取订单库中的配置表的自动收货超时时间
        OrderConfig orderConfig = orderConfigMapper.selectByPrimaryKey(1);
        //2. 获取当前系统日期
        Integer takeTimeout = orderConfig.getTakeTimeout();
        //3. 当前日期 - 收货超时时间 = 具体超时的下单日期
        LocalDate date = LocalDate.now();
        LocalDate timeOutDate = date.plusDays(-takeTimeout);
        //4. 到订单表中查询, 订单创建时间小于等于具体超时的下单日期的订单集合数据
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        //支付状态为, 1已支付
        criteria.andEqualTo("payStatus", "1");
        //订单创建时间小于等于具体的超时日期的
        criteria.andLessThanOrEqualTo("createTime", timeOutDate);
        List<Order> orderList = orderMapper.selectByExample(example);
        //5. 遍历查询到的订单集合数据
        if (orderList != null) {
            for (Order order : orderList) {
                //6. 执行自动收货
                orderService.recive(order.getId());
            }
        }
    }
}
