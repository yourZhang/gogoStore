
package com.store.search.listener;

import com.store.search.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "search_add_queue")
public class UpGoodsListener {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ManagerService managerService;

    /**
     * 功能描述: <br>
     * 〈商品上架消息〉
     *
     * @Param: [spuId]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/2 18:20
     */
    @RabbitHandler
    public void receiveMsg(String spuId) {
        logger.info("接收到上架spuId: {}", spuId);
        managerService.importBySpuId(spuId);
    }
}