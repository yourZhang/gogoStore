package com.store.web.utils;

/**
 * 这里定义的路径需要拦截, 必须登录, 有权限后才可以访问
 */
public class UrlFilter  {

    //购物车订单微服务都需要用户登录，必须携带令牌，所以所有路径都过滤,订单微服务需要过滤的地址
    public static String orderFilterPath = "/api/wseckillorder/**,/api/seckillorder/**,/api/wpay,/api/wpay/**,/api/worder/**,/api/user/**,/api/address/**,/api/wcart/**,/api/cart/**,/api/categoryReport/**,/api/orderConfig/**,/api/order/**,/api/orderItem/**,/api/orderLog/**,/api/preferential/**,/api/returnCause/**,/api/returnOrder/**,/api/returnOrderItem/**";


    /**
     * 判断路径是否该被拦截, 该拦截返回true, 不该拦截返回false
     * @param url
     * @return
     */
    public static boolean hasAuthorize(String url){

        String[] strings = orderFilterPath.replace("**", "").split(",");
        for (String uri : strings) {

            if (url.startsWith(uri)){
                return true;
            }
        }
        return false;
    }
}
