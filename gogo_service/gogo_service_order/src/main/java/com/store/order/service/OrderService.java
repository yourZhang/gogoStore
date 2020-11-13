package com.store.order.service;


import com.github.pagehelper.Page;
import com.store.entity.Result;
import com.store.order.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {

    /***
     * 查询所有
     * @return
     */
    List<Order> findAll();

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Order findById(String id);

    /***
     * 新增
     * @param order
     */
    void add(Order order);

    /***
     * 修改
     * @param order
     */
    void update(Order order);

    /***
     * 删除
     * @param id
     */
    void delete(String id);

    /***
     * 多条件搜索
     * @param searchMap
     * @return
     */
    List<Order> findList(Map<String, Object> searchMap);

    /***
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    Page<Order> findPage(int page, int size);

    /***
     * 多条件分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    Page<Order> findPage(Map<String, Object> searchMap, int page, int size);

    /**
     * 支付成功订单业务处理
     * @param transId   交易流水号
     * @param orderId   订单号
     */
    public void paySuccessOrder(String transId, String orderId);

    /**
     * 支付超时业务处理
     * @param orderId
     */
    public void cancelPayOrder(String orderId);

    /**
     * 批量发货
     * @param orderList
     * @return
     */
    public Result bashSend(List<Order> orderList);

    /**
     * 手动收货
     * @param orderId
     * @return
     */
    public Result recive(String orderId);

}
