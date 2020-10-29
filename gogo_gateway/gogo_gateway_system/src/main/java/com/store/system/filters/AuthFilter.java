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
package com.store.system.filters;

import com.store.system.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter,Ordered{

    /*
        校验token
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求对象和响应对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //2.获取请求url
        String url = request.getURI().getPath();
        //3.判断请求url是否是登录请求
        if(url.contains("login")){
            //4.是登录请求-----》放行
            return chain.filter(exchange);
        }
        //5.不是登录请求---》请求头获取token
        String token = request.getHeaders().get("token").get(0);
        //6.校验token
        try {
            Claims claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //401 校验失败token非法
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return 0;
    }
}
