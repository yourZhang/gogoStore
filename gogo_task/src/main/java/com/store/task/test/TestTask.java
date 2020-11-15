package com.store.task.test;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class TestTask {

    /**
     * 每秒都执行一次 : * * * * * ?
     * 每分钟的第0秒开始执行, 每增加10秒执行一次: 0/10 * * * * ?
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void test() {
        System.out.println("========定时方法执行了一次========");
    }
}
