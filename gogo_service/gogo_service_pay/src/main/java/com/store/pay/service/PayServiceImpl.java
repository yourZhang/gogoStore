package com.store.pay.service;

import com.github.wxpay.sdk.WXPay;
import com.store.entity.Constants;
import com.store.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WXPay wxPay;

    //读取配置文件中的回调地址
    @Value("${wxpay.notify_url}")
    private String notifyUrl;

    @Override
    public Map<String, String> nativePay(String userName) {

        /**
         * 1. 根据用户名获取redis中的待支付订单对象
         */
        Order order = (Order) redisTemplate.boundValueOps(Constants.REDIS_ORDER_PAY + userName).get();
        if (order == null) {
            throw new RuntimeException("订单对象为空, 没有订单可以支付!");
        }

        /**
         * 2. 封装微信统一下单接口参数
         */
        Map<String,String> map=new HashMap<>();
        map.put("body","gogo-store");//商品描述
        map.put("out_trade_no", order.getId());//订单号, 订单号不允许重复
        //order.getPayMoney()
        map.put("total_fee", "1");//金额, 微信支付金额的单位是分
        map.put("spbill_create_ip","127.0.0.1");//终端IP
        map.put("notify_url", notifyUrl);//回调地址, 必须是有效的公网地址, 告诉微信支付成功后通知这个地址支付结果.
        map.put("trade_type","NATIVE");//交易类型
        /**
         * 3. 调用微信统一下单接口, 返回支付链接等数据
         */
        Map<String, String> resultMap = null;
        try {
            resultMap = wxPay.unifiedOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 4. 封装返回的数据格式
         */
        //订单id
        resultMap.put("orderId", order.getId());
        //支付金额
        resultMap.put("payMoney", String.valueOf(order.getPayMoney()));
        /**
         * 5. 返回
         */
        return resultMap;
    }

    @Override
    public Map<String, String> queryPay(String orderId) {

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("out_trade_no", orderId);

        try {
            Map<String, String> resultMap = wxPay.orderQuery(paramMap);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> closePay(String orderId) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("out_trade_no", orderId);

        try {
            Map<String, String> resultMap = wxPay.closeOrder(paramMap);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
