package com.store.order.feign;


import com.store.order.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@FeignClient(name = "order")
@RequestMapping("/order")
public interface OrderFeign {

    /**
     * 功能描述: <br>
     * 〈提交订单〉
     *
     * @Param: [order]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/11 20:01
     */
    @PostMapping
    public void add(@RequestBody Order order);
}
