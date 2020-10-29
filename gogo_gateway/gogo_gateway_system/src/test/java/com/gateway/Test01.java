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
package com.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

public class Test01 {

    /*
        eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJzdWIiOiLov5nmmK_mtYvor5XmlbDmja4iLCJpYXQiOjE2MDM5NDM3ODd9.UASTnH0fPW7ixaxQkdNExdWbOYMNZzYn0pR_zKTIlVs
     */
    @Test
    public void createJwt() {

        long l = System.currentTimeMillis() + 100000;

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "itheima-22")
                .setId("123")
                .setSubject("这是测试数据")
                .setIssuedAt(new Date())//创建时间
                .setExpiration(new Date(l)) //过期时间
                //用户自定义数据
                .claim("username", "张三")
                .claim("age", 23)
                .compact();
        System.out.println(jwt);
    }

    @Test
    public void parseJwt() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJzdWIiOiLov5nmmK_mtYvor5XmlbDmja4iLCJpYXQiOjE2MDM5NDQ1NDQsImV4cCI6MTYwMzk0NDY0MywidXNlcm5hbWUiOiLlvKDkuIkiLCJhZ2UiOjIzfQ.IMs8QKGjLjFaMdCm9UDoad9qQRIxS3lZE7IxsqqWrqc";
        Jws<Claims> claimsJws = Jwts.parser().
                setSigningKey("itheima-2")
                .parseClaimsJws(jwt);
        System.out.println(claimsJws.getBody());
    }
}
