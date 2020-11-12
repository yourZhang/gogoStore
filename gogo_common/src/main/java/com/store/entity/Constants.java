package com.store.entity;

/**
 * 常量
 * @author ZJ
 */
public class Constants {
    //购物车
    public static final String REDIS_CART = "cart_";

    //待支付订单key
    public final static String REDIS_ORDER_PAY = "order_pay_";
    //秒杀商品key
    public static final String SECKILL_GOODS_KEY="seckill_goods_";
    //秒杀商品库存数key
    public static final String SECKILL_GOODS_STOCK_COUNT_KEY="seckill_goods_stock_count_";
    //秒杀用户key
    public static final String SECKILL_USER_KEY = "seckill_user_";


    /**
     * 订单状态
     */
    //待支付
    public static final String ORDER_STATUS_0 = "0";
    //已支付
    public static final String ORDER_STATUS_1 = "1";
    //待发货
    public static final String ORDER_STATUS_2 = "2";
    //已发货
    public static final String ORDER_STATUS_3 = "3";
    //待收货
    public static final String ORDER_STATUS_4 = "4";
    //已收货
    public static final String ORDER_STATUS_5 = "5";
    //待退款
    public static final String ORDER_STATUS_6 = "6";
    //已退款
    public static final String ORDER_STATUS_7 = "7";
    //订单正常完结
    public static final String ORDER_STATUS_8 = "8";
    //订单超时结束
    public static final String ORDER_STATUS_9 = "9";
}
