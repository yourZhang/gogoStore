/*
#          .,:,,,                                        .::,,,::.          
#        .::::,,;;,                                  .,;;:,,....:i:         
#        :i,.::::,;i:.      ....,,:::::::::,....   .;i:,.  ......;i.        
#        :;..:::;::::i;,,:::;:,,,,,,,,,,..,.,,:::iri:. .,:irsr:,.;i.        
#        ;;..,::::;;;;ri,,,.                    ..,,:;s1s1ssrr;,.;r,        
#        :;. ,::;ii;:,     . ...................     .;iirri;;;,,;i,        
#        ,i. .;ri:.   ... ............................  .,,:;:,,,;i:        
#        :s,.;r:... ....................................... .::;::s;        
#        ,1r::. .............,,,.,,:,,........................,;iir;        
#        ,s;...........     ..::.,;:,,.          ...............,;1s        
#       :i,..,.              .,:,,::,.          .......... .......;1,       
#      ir,....:rrssr;:,       ,,.,::.     .r5S9989398G95hr;. ....,.:s,      
#     ;r,..,s9855513XHAG3i   .,,,,,,,.  ,S931,.,,.;s;s&BHHA8s.,..,..:r:     
#    :r;..rGGh,  :SAG;;G@BS:.,,,,,,,,,.r83:      hHH1sXMBHHHM3..,,,,.ir.    
#   ,si,.1GS,   sBMAAX&MBMB5,,,,,,:,,.:&8       3@HXHBMBHBBH#X,.,,,,,,rr    
#   ;1:,,SH:   .A@&&B#&8H#BS,,,,,,,,,.,5XS,     3@MHABM&59M#As..,,,,:,is,   
#  .rr,,,;9&1   hBHHBB&8AMGr,,,,,,,,,,,:h&&9s;   r9&BMHBHMB9:  . .,,,,;ri.  
#  :1:....:5&XSi;r8BMBHHA9r:,......,,,,:ii19GG88899XHHH&GSr.      ...,:rs.  
#  ;s.     .:sS8G8GG889hi.        ....,,:;:,.:irssrriii:,.        ...,,i1,  
#  ;1,         ..,....,,isssi;,        .,,.                      ....,.i1,  
#  ;h:               i9HHBMBBHAX9:         .                     ...,,,rs,  
#  ,1i..            :A#MBBBBMHB #  .r1,..        ,..;3BMBBBHBB#Bh.     ..                    ....,,,,,i1;   
#   :h;..       .,..;,1XBMMMMBXs,.,, .. :: ,.               ....,,,,,,ss.   
#    ih: ..    .;;;, ;;:s58A3i,..    ,. ,.:,,.             ...,,,,,:,s1,    
#    .s1,....   .,;sh,  ,iSAXs;.    ,.  ,,.i85            ...,,,,,,:i1;     
#     .rh: ...     rXG9XBBM#M#MHAX3hss13&&HHXr         .....,,,,,,,ih;      
#      .s5: .....    i598X&&A&AAAAAA&XG851r:       ........,,,,:,,sh;       
#      . ihr, ...  .         ..                    ........,,,,,;11:.       
#         ,s1i. ...  ..,,,..,,,.,,.,,.,..       ........,,.,,.;s5i.         
#          .:s1r,......................       ..............;shs,           
#          . .:shr:.  ....                 ..............,ishs.             
#              .,issr;,... ...........................,is1s;.               
#                 .,is1si;:,....................,:;ir1sr;,                  
#                    ..:isssssrrii;::::::;;iirsssssr;:..                    
#                         .,::iiirsssssssssrri;;:.
*/
package com.store.business.listener;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "ad_update_queue")
public class AdListener {

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 功能描述: <br>
     * 〈接受mq消息，用于更新redis中的广告轮播图〉
     *
     * @Param: [msg]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/2 17:28
     */
    @RabbitHandler //转换数据
    public void receiveMsg(String msg) {
        logger.info("接收到大广告数据： {}", msg);
        //发送请求--->http://192.168.200.128/ad_update?position=web_index_lb
        String url = "http://192.168.200.128/ad_update?position=" + msg;
        //1.创建okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建请求对象封装请求地址
        Request request = new Request.Builder()
                .url(url)
                .build();
        //3.发送请求，重写成功和失败的方法
        okHttpClient.newCall(request).enqueue(new Callback() {
            /*
                失败时会回调
             */
            @Override
            public void onFailure(Call call, IOException e) {
                logger.error("请求大广告预热失败： {}", e.getMessage());
            }

            /*
                成功时会回调
             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                logger.error("请求大广告预热成功： {}", response.message());
            }
        });
    }
}
