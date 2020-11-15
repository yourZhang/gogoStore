package com.store.task.task;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务, 定时触发
 * 自动收货
 */
//@Component
public class AutoTackTask {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 定时自动收货
     * 明天凌晨三点自动触发: 0 0 3 * * ?
     * 现在测试每分钟触发一次: 0 * * * * ?
     */
    @Scheduled(cron = "0 * * * * ?")
    public void task() {
        rabbitTemplate.convertAndSend("", "order_tack", "-");
    }
}
