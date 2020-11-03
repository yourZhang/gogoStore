package com.store.search.listener;

import com.store.search.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: gogo-store
 * @description:
 * @author: xiaozhang6666
 * @create: 2020-11-03 11:40
 **/
@Component
@RabbitListener(queues = "search_del_queue")
public class DownGoodsListener {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ManagerService managerService;

    @RabbitHandler
    public void receiveMsg(String spuId) {
        logger.info("supId是==={}", spuId);
        managerService.downBySpuId(spuId);
    }
}
