package com.store.order.service.impl;


import com.store.entity.Constants;
import com.store.feign.SkuFeign;
import com.store.feign.SpuFeign;
import com.store.order.pojo.OrderItem;
import com.store.order.service.CartService;
import com.store.pojo.Sku;
import com.store.pojo.Spu;
import com.store.util.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SpuFeign spuFeign;

    @Autowired
    private IdWorker idWorker;

    @Override
    public Map<String, Object> list(String userName) {
        //1. 根据用户名到redis中获取购物车列表
        List<OrderItem> cartList = (List<OrderItem>) redisTemplate.boundHashOps(Constants.REDIS_CART + userName).values();
        //2. 计算购物车中的总购买数量和总购买金额
        //总购买数量
        int totalCount = 0;
        //总购买金额
        int totalPrice = 0;

        if (cartList != null) {
            for (OrderItem orderItem : cartList) {
                totalCount += orderItem.getNum();
                totalPrice += orderItem.getMoney();
            }
        }

        //3. 将计算得出的总购买数量和总购买金额还有购物车列表数据封装成map返回
        Map<String, Object> resultMap = new HashMap<>();
        //购物项集合
        resultMap.put("orderItemList", cartList);
        //总购买数量
        resultMap.put("totalNum", totalCount);
        //总购买金额
        resultMap.put("totalPrice", totalPrice);

        return resultMap;
    }

    @Override
    public void addAndUpdate(String skuId, Integer num, String userName) {
        //1. 根据用户名和skuId到redis中获取购物项对象
        OrderItem orderItem = (OrderItem) redisTemplate.boundHashOps(Constants.REDIS_CART + userName).get(skuId);

        //2. 判断是否能够获取到购物项对象
        if (orderItem != null) {
            //3. 如果获取到购物项对象则更改购买数量, 支付金额等数据
            //计算最新的购买数量 = 老的购买数量 + 现在的购买数量
            orderItem.setNum(orderItem.getNum() + num);
            //由于num可以是负数, 所以要判断最新的购买数量如果小于等于0, 将购物项从购物车中删除
            if (orderItem.getNum() <= 0) {
                redisTemplate.boundHashOps(Constants.REDIS_CART + userName).delete(skuId);
                return;
            }
            //总价 = 单价 * 最新购买数量
            orderItem.setMoney(orderItem.getNum() * orderItem.getPrice());
            //支付价钱
            orderItem.setPayMoney(orderItem.getNum() * orderItem.getPrice());
        } else {
            //4. 如果获取不到购物项对象, 新建购物项对象
            orderItem = createOrderItem(skuId, num, userName);
        }

        //5. 将最新的购物项对象放回到redis中覆盖redis中的老数据
        redisTemplate.boundHashOps(Constants.REDIS_CART + userName).put(skuId, orderItem);
    }

    /**
     * 新建购物项对象
     *
     * @param skuId    购买商品的库存id
     * @param num      购买数量
     * @param userName 消费者用户名
     * @return
     */
    private OrderItem createOrderItem(String skuId, Integer num, String userName) {
        if (num <= 0) {
            throw new RuntimeException("购买数量不正确, 至少购买1件产品");
        }
        if (StringUtils.isEmpty(userName)) {
            throw new RuntimeException("请登录后再购买商品!");
        }

        //根据库存id, 获取库存对象
        Sku sku = skuFeign.findOneById(skuId);
        if (sku == null) {
            throw new RuntimeException("数据库中没有找到对应的sku库存数据, skuid=" + skuId);
        }

        //根据商品id, 获取商品对象
        Spu spu = spuFeign.findByIds(sku.getSpuId());
        if (spu == null) {
            throw new RuntimeException("数据库中没有找到对应的spu商品数据, spuId=" + sku.getSpuId());
        }

        OrderItem orderItem = new OrderItem();
        //购物项主键id
        orderItem.setId(String.valueOf(idWorker.nextId()));
        //购买数量
        orderItem.setNum(num);
        //支付价格
        orderItem.setPayMoney(sku.getPrice() * num);
        //总价格
        orderItem.setMoney(sku.getPrice() * num);
        //购物车复选框是否勾选
        orderItem.setChecked(true);
        //商品id
        orderItem.setSpuId(sku.getSpuId());
        //库存id
        orderItem.setSkuId(skuId);
        //商品名称
        orderItem.setName(sku.getName());
        //运费
        orderItem.setPostFee(0);
        //示例图片
        orderItem.setImage(sku.getImage());
        //商品一级分类
        orderItem.setCategoryId1(spu.getCategory1Id());
        //二级分类
        orderItem.setCategoryId2(spu.getCategory2Id());
        //三级分类
        orderItem.setCategoryId3(spu.getCategory3Id());
        //商品重量
        orderItem.setWeight(sku.getWeight());
        //商品单价
        orderItem.setPrice(sku.getPrice());
        return orderItem;
    }
}
