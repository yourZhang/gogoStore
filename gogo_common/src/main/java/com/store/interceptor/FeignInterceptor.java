package com.store.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 自定义拦截器, 拦截所有请求
 * 每次微服务调用之前都先检查下头文件，将请求的头文件中的令牌数据再放入到header中
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //获取所有请求属性
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        //判断请求属性不为空
        if (requestAttributes!=null){

            //将请求属性对象强转成HttpServletRequest对象
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (request!=null){
                //获取所有请求属性中的属性名字集合
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames!=null){
                    //遍历所有请求属性名字的集合
                    while (headerNames.hasMoreElements()){
                        //获取每个请求属性的名字
                        String headerName = headerNames.nextElement();
                        //判断请求属性的名字是否叫做authorization
                        if (headerName.equals("authorization")){
                            //根据请求属性名字, 获取请求属性的值, 这个值就是jwt
                            String headerValue = request.getHeader(headerName);
                            //将这个请求的名字和值jwt放入feign远程调用请求头中
                            requestTemplate.header(headerName,headerValue);
                        }
                    }
                }
            }
        }
    }

}
