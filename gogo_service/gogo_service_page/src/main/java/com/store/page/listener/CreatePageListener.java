
package com.store.page.listener;

import com.store.page.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "page_create_queue")
public class CreatePageListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PageService pageService;

    @RabbitHandler
    public void receiveMsg(String spuId ){
        logger.info("接收到上架的消息，生成页面 spuId: {}", spuId);
        pageService.createHtml(spuId);
    }
}
