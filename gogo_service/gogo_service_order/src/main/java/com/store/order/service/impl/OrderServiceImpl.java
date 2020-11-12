package com.store.order.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.netflix.discovery.converters.Auto;
import com.store.entity.Constants;
import com.store.feign.SkuFeign;
import com.store.order.dao.OrderItemMapper;
import com.store.order.dao.OrderLogMapper;
import com.store.order.dao.OrderMapper;
import com.store.order.pojo.Order;
import com.store.order.pojo.OrderItem;
import com.store.order.pojo.OrderLog;
import com.store.order.service.CartService;
import com.store.order.service.OrderItemService;
import com.store.order.service.OrderService;
import com.store.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private CartService cartService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    /**
     * 查询全部列表
     *
     * @return
     */
    @Override
    public List<Order> findAll() {
        return orderMapper.selectAll();
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Order findById(String id) {
        return orderMapper.selectByPrimaryKey(id);
    }


    /**
     * 功能描述: <br>
     * 〈增加〉
     *
     * @Param: [order]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/12 15:38
     */
    @Override
    public void add(Order order) {
        //* 1. 获取redis中的购物车
        Map<String, Object> cartMap = cartService.buyList(order.getUsername());
        if (cartMap == null) {
            throw new RuntimeException("购物车中没有商品, 无法提交订单");
        }
        //获取购物项列表
        List<OrderItem> cartList = (List<OrderItem>) cartMap.get("orderItemList");
        //获取总购买数量
        int totalCount = Integer.parseInt(String.valueOf(cartMap.get("totalNum")));
        //获取总购买金额
        int totalPrice = Integer.parseInt(String.valueOf(cartMap.get("totalPrice")));
        if (cartList == null || cartList.size() == 0) {
            throw new RuntimeException("购物车中没有商品, 无法提交订单");
        }

        /**
         * 2. 根据购物车保存订单到mysql数据库的订单表中
         */
        //订单主键id
        order.setId(String.valueOf(idWorker.nextId()));
        //订单创建时间
        order.setCreateTime(new Date());
        //数据修改时间
        order.setUpdateTime(new Date());
        //订单状态, 0待支付
        order.setOrderStatus(Constants.ORDER_STATUS_0);
        //发货状态, 0未发货
        order.setConsignStatus("0");
        //支付状态, 0未支付
        order.setPayStatus("0");
        //支付金额
        order.setPayMoney(totalPrice);
        //订单数据来源, 1:web，2：app，3：微信公众号，4：微信小程序  5 H5手机页面
        order.setSourceType("1");
        //运费
        order.setPostFee(0);
        //优惠金额
        order.setPreMoney(0);
        //总金额
        order.setTotalMoney(totalPrice);
        //总购买数量
        order.setTotalNum(totalCount);
        orderMapper.insertSelective(order);
        /**
         * 3. 保存订单数据到redis中作为待支付订单使用, 后续支付业务需要
         */
        redisTemplate.boundValueOps(Constants.REDIS_ORDER_PAY + order.getUsername()).set(order);
        /**
         * 4. 保存订单详情到mysql数据库的订单详情表, 一个购物项就是一条订单详情数据
         */
        for (OrderItem orderItem : cartList) {
            //将订单id, 放入订单详情对象中, 建立订单和订单详情的关系
            orderItem.setOrderId(order.getId());
            orderItemMapper.insertSelective(orderItem);
            /**
             * 5. 到mysql数据库的sku库存表中扣减库存, 增加销量
             */
            skuFeign.decrCount(orderItem.getSkuId(), orderItem.getNum());
            /**
             * 6. 清除redis中购买过的购物项
             */
            cartService.delete(orderItem.getSkuId(), order.getUsername());
        }
    }


    /**
     * 修改
     *
     * @param order
     */
    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKey(order);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        orderMapper.deleteByPrimaryKey(id);
    }


    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    @Override
    public List<Order> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return orderMapper.selectByExample(example);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Order> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        return (Page<Order>) orderMapper.selectAll();
    }

    /**
     * 条件+分页查询
     *
     * @param searchMap 查询条件
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @Override
    public Page<Order> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        return (Page<Order>) orderMapper.selectByExample(example);
    }

    @Override
    public void paySuccessOrder(String transId, String orderId) {
        //从数据库中查询订单对象数据
        Order order = orderMapper.selectByPrimaryKey(orderId);
        /**
         * 1. 修改订单支付状态
         */
        //支付状态, 1已支付
        order.setPayStatus("1");
        //订单状态, 1已支付
        order.setOrderStatus(Constants.ORDER_STATUS_1);
        //订单更新时间
        order.setUpdateTime(new Date());
        //支付时间
        order.setPayTime(new Date());
        //交易流水号
        order.setTransactionId(transId);
        orderMapper.updateByPrimaryKeySelective(order);
        /**
         * 2. 删除redis中的待支付订单对象
         */
        redisTemplate.delete(Constants.REDIS_ORDER_PAY + order.getUsername());

        /**
         * 3. 记录订单变动日志, 到订单日志表
         */
        OrderLog orderLog = new OrderLog();
        //日志主键id
        orderLog.setId(String.valueOf(idWorker.nextId()));
        //订单状态, 1已支付
        orderLog.setOrderStatus(Constants.ORDER_STATUS_1);
        //订单id
        orderLog.setOrderId(orderId);
        //操作时间
        orderLog.setOperateTime(new Date());
        //发货状态, 0未发货
        orderLog.setConsignStatus("0");
        //操作人, system系统自动操作
        orderLog.setOperater("system");
        //支付状态, 1已支付
        orderLog.setPayStatus("1");
        orderLogMapper.insertSelective(orderLog);
    }

    /**
     * 构建查询对象
     *
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 订单id
            if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 支付类型，1、在线支付、0 货到付款
            if (searchMap.get("payType") != null && !"".equals(searchMap.get("payType"))) {
                criteria.andEqualTo("payType", searchMap.get("payType"));
            }
            // 物流名称
            if (searchMap.get("shippingName") != null && !"".equals(searchMap.get("shippingName"))) {
                criteria.andLike("shippingName", "%" + searchMap.get("shippingName") + "%");
            }
            // 物流单号
            if (searchMap.get("shippingCode") != null && !"".equals(searchMap.get("shippingCode"))) {
                criteria.andLike("shippingCode", "%" + searchMap.get("shippingCode") + "%");
            }
            // 用户名称
            if (searchMap.get("username") != null && !"".equals(searchMap.get("username"))) {
                criteria.andLike("username", "%" + searchMap.get("username") + "%");
            }
            // 买家留言
            if (searchMap.get("buyerMessage") != null && !"".equals(searchMap.get("buyerMessage"))) {
                criteria.andLike("buyerMessage", "%" + searchMap.get("buyerMessage") + "%");
            }
            // 是否评价
            if (searchMap.get("buyerRate") != null && !"".equals(searchMap.get("buyerRate"))) {
                criteria.andLike("buyerRate", "%" + searchMap.get("buyerRate") + "%");
            }
            // 收货人
            if (searchMap.get("receiverContact") != null && !"".equals(searchMap.get("receiverContact"))) {
                criteria.andLike("receiverContact", "%" + searchMap.get("receiverContact") + "%");
            }
            // 收货人手机
            if (searchMap.get("receiverMobile") != null && !"".equals(searchMap.get("receiverMobile"))) {
                criteria.andLike("receiverMobile", "%" + searchMap.get("receiverMobile") + "%");
            }
            // 收货人地址
            if (searchMap.get("receiverAddress") != null && !"".equals(searchMap.get("receiverAddress"))) {
                criteria.andLike("receiverAddress", "%" + searchMap.get("receiverAddress") + "%");
            }
            // 订单来源：1:web，2：app，3：微信公众号，4：微信小程序  5 H5手机页面
            if (searchMap.get("sourceType") != null && !"".equals(searchMap.get("sourceType"))) {
                criteria.andEqualTo("sourceType", searchMap.get("sourceType"));
            }
            // 交易流水号
            if (searchMap.get("transactionId") != null && !"".equals(searchMap.get("transactionId"))) {
                criteria.andLike("transactionId", "%" + searchMap.get("transactionId") + "%");
            }
            // 订单状态
            if (searchMap.get("orderStatus") != null && !"".equals(searchMap.get("orderStatus"))) {
                criteria.andEqualTo("orderStatus", searchMap.get("orderStatus"));
            }
            // 支付状态
            if (searchMap.get("payStatus") != null && !"".equals(searchMap.get("payStatus"))) {
                criteria.andEqualTo("payStatus", searchMap.get("payStatus"));
            }
            // 发货状态
            if (searchMap.get("consignStatus") != null && !"".equals(searchMap.get("consignStatus"))) {
                criteria.andEqualTo("consignStatus", searchMap.get("consignStatus"));
            }
            // 是否删除
            if (searchMap.get("isDelete") != null && !"".equals(searchMap.get("isDelete"))) {
                criteria.andEqualTo("isDelete", searchMap.get("isDelete"));
            }

            // 数量合计
            if (searchMap.get("totalNum") != null) {
                criteria.andEqualTo("totalNum", searchMap.get("totalNum"));
            }
            // 金额合计
            if (searchMap.get("totalMoney") != null) {
                criteria.andEqualTo("totalMoney", searchMap.get("totalMoney"));
            }
            // 优惠金额
            if (searchMap.get("preMoney") != null) {
                criteria.andEqualTo("preMoney", searchMap.get("preMoney"));
            }
            // 邮费
            if (searchMap.get("postFee") != null) {
                criteria.andEqualTo("postFee", searchMap.get("postFee"));
            }
            // 实付金额
            if (searchMap.get("payMoney") != null) {
                criteria.andEqualTo("payMoney", searchMap.get("payMoney"));
            }

        }
        return example;
    }

}
