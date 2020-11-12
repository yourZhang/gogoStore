package com.store.web.order.controller;


import com.store.order.feign.CartFeign;
import com.store.order.feign.OrderFeign;
import com.store.order.pojo.Order;
import com.store.user.feign.AddressFeign;
import com.store.user.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Map;

/**
 * 订单处理controller
 */
@Controller
@RequestMapping("/worder")
public class WebOrderController {

    @Autowired
    private AddressFeign addressFeign;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private OrderFeign orderFeign;


    /**
     * 功能描述: <br>
     * 〈跳转到订单结算页面〉
     *
     * @Param: [model]
     * @return: java.lang.String
     * @Author: xiaozhang666
     * @Date: 2020/11/11 20:40
     */
    @GetMapping("/ready")
    public String ready(Model model) {
        //1. 通过feign远程调用用户微服务, 获取消费者收货地址列表
        List<Address> addressList = addressFeign.findListByUserName();
        Address defaultAddress = null;
        //2. 从地址列表中获取默认收货人地址
        if (addressList != null) {
            for (Address address : addressList) {
                if ("1".equals(address.getIsDefault())) {
                    defaultAddress = address;
                }
            }
        }
        //3. 获取复选框勾选的购物车商品数据展示
        Map<String, Object> cartMap = cartFeign.buyList();
        model.addAttribute("cartMap", cartMap);
        model.addAttribute("addressList", addressList);
        model.addAttribute("defaultAddress", defaultAddress);
        return "order";
    }

    @PostMapping("/submit")
    public String submit(Order order) {
        //通过feign远程调用订单业务微服务
        orderFeign.add(order);
        return "redirect:http://web.changgou.com:8001/api/wpay/nativePay";
    }
}
