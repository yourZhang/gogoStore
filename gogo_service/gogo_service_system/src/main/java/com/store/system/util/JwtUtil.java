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
package com.store.system.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {



    //有效期为
    public static final Long JWT_TTL = 3600000L;// 60 * 60 *1000  一个小时
    //设置秘钥明文
    public static final String JWT_KEY = "itcast";

    /**
     * 创建token
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if(ttlMillis==null){
            ttlMillis=JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        SecretKey secretKey = generalKey();

        JwtBuilder builder = Jwts.builder()
                .setId(id)              //唯一的ID
                .setSubject(subject)   // 主题  可以是JSON数据
                .setIssuer("admin")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成加密后的秘钥 secretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}
