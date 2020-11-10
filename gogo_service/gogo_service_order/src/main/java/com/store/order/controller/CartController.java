package com.store.order.controller;


import com.store.order.config.TokenDecode;
import com.store.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 购物车业务controller
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private TokenDecode tokenDecode;

    @Autowired
    private CartService cartService;

    /**
     * 查询购物车数据返回
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list() {
        //1. 获取当前登录用户的用户名
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");

        //2. 根据用户名获取这个人的购物车数据
        Map<String, Object> resultMap = cartService.list(username);

        //3. 返回这个人的购物车数据
        return resultMap;
    }

    /**
     * 添加修改购物车中的购物项
     * @param skuId 库存id
     * @param num   购买数量
     */
    @GetMapping("/add/{skuId}/{num}")
    public void addAndUpdate(@PathVariable(value = "skuId") String skuId, @PathVariable(value = "num")Integer num) {
        //1. 获取当前登录用户的用户名
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");

        //2. 调用service进行购物车的添加或者修改
        cartService.addAndUpdate(skuId, num, username);

    }
}
