package com.store.order.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {


    //延迟消息队列名称
    public static final String RELAY_QUEUE = "relay_queue";
    //死信交换机名称
    public static final String CANCEL_ORDER_PAY_EXCHANGE = "cancel_order_pay_exchange";

    //死信队列名称
    public static final String CANCEL_ORDER_QUEUE = "cancel_order_queue";

    //创建延迟消息队列, 并和死信交换机绑定
    @Bean
    public Queue relayQueue(){
        return QueueBuilder.durable(RELAY_QUEUE)
                .withArgument("x-dead-letter-exchange",CANCEL_ORDER_PAY_EXCHANGE)
                .withArgument("x-dead-letter-routing-key","")
                .build();
    }


    /**
     * 创建死信队列
     * @return
     */
    @Bean
    public Queue cancelOrderQueue(){
        return new Queue(CANCEL_ORDER_QUEUE);
    }

    /**
     * 创建死信交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(CANCEL_ORDER_PAY_EXCHANGE);
    }

    /**
     * 绑定死信交换机和死信队列
     * @return
     */
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(cancelOrderQueue()).to(directExchange()).with("");
    }


}
