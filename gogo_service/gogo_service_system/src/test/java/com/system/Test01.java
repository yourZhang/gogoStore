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
package com.system;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;


public class Test01 {
    
    @Test
    public void createBcrypt(){
        /*
            盐：$2a$10$sHjqQiP2JBSiQywgkXrQsO
            密文： $2a$10$sHjqQiP2JBSiQywgkXrQsO kBH5krCsTEJBi7Cf5RO8AemUXAIacL6
         */
        String gensalt = BCrypt.gensalt();
        System.out.println("盐：" + gensalt);
        String hashpw = BCrypt.hashpw("123456", gensalt);
        System.out.println("密文： " + hashpw);
    }

    @Test
    public void checkPw(){
        String pw = "$2a$10$sHjqQiP2JBSiQywgkXrQsOkBH5krCsTEJBi7Cf5RO8AemUXAIacL6";
        boolean checkpw = BCrypt.checkpw("123456", pw);
        System.out.println("是否校验通过： " + checkpw);
    }
}