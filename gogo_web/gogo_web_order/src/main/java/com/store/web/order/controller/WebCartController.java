package com.store.web.order.controller;


import com.store.order.feign.CartFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 购物车页面渲染
 */
@Controller
@RequestMapping("/wcart")
public class WebCartController {

    @Autowired
    private CartFeign cartFeign;

    /**
     * 查询购物车列表数据, 返回到购物车页面
     * @return
     */
    @GetMapping("/list")
    public String list(Model model) {
        //feign调用订单业务微服务, 查询购物车列表数据返回
        Map<String, Object> cartMap = cartFeign.list();

        model.addAttribute("result", cartMap);
        return "cart";
    }

    /**
     * 购物车添加和修改购物项
     * @param skuId 库存id
     * @param num   购买数量(购买数量可能是正数也可能是负数)
     * @return
     */
    @GetMapping("/add")
    public String addAndUpdate(String skuId, Integer num) {
        //feign调用订单业务微服务, 添加和修改购物车中的购物项数据
        cartFeign.addAndUpdate(skuId, num);
        //重定向到, 查询购物车列表方法
        return "redirect:http://web.changgou.com:8001/api/wcart/list";
    }
}
