package com.store.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@CanalEventListener
public class BusinessListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 功能描述: <br>
     * 〈监听数据库 发送mq消息〉
     *
     * @Param: [eventType, rowData]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/2 17:16
     */
    @ListenPoint(schema = "changgou_business", table = {"tb_ad"})
    public void adUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.err.println("广告数据发生变化");
        //修改前数据
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            //http://192.168.200.128/ad_update?position=web_index_lb
            if (column.getName().equals("position")) {
                System.out.println("发送消息到mq  ad_update_queue:" + column.getValue());
                rabbitTemplate.convertAndSend("", "ad_update_queue", column.getValue());  //发送消息到mq
                break;
            }
        }

        //修改后数据
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
//            if(column.getName().equals("position")){
//                System.out.println("发送消息到mq  ad_update_queue:"+column.getValue());
//                rabbitTemplate.convertAndSend("","ad_update_queue",column.getValue());  //发送消息到mq
//                break;
//            }
        }
    }
}
