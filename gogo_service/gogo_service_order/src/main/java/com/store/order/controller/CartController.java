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
     * 功能描述: <br>
     * 〈查询购物车数据返回〉
     *
     * @Param: []
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: xiaozhang666
     * @Date: 2020/11/11 15:00
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
     * 功能描述: <br>
     * 〈获取消费者购物车(复选框都为勾选的购物项)〉
     *
     * @Param: []
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: xiaozhang666
     * @Date: 2020/11/11 20:43
     */
    @GetMapping("/buyList")
    public Map<String, Object> buyList() {
        //1. 获取当前登录用户的用户名
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");

        //2. 根据用户名获取这个人的购物车数据
        Map<String, Object> resultMap = cartService.buyList(username);
        return resultMap;
    }

    /**
     * 功能描述: <br>
     * 〈添加修改购物车中的购物项〉
     *
     * @Param: [skuId 购物车中sku的id, num 购物车数量]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/11 15:00
     */
    @GetMapping("/add/{skuId}/{num}")
    public void addAndUpdate(@PathVariable(value = "skuId") String skuId, @PathVariable(value = "num") Integer num) {
        //1. 获取当前登录用户的用户名
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");
        //2. 调用service进行购物车的添加或者修改
        cartService.addAndUpdate(skuId, num, username);
    }


    /**
     * 功能描述: <br>
     * 〈删除购物车中的购物项〉
     *
     * @Param: [skuId]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/11 19:26
     */
    @GetMapping("/delete/{skuId}")
    public void delete(@PathVariable(value = "skuId") String skuId) {
        //1. 获取当前登录用户的用户名
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");

        //2. 调用service删除
        cartService.delete(skuId, username);
    }


    /**
     * 功能描述: <br>
     * 〈更新购物车复选框状态〉
     *
     * @Param: [skuId, checked]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/11 19:46
     */
    @GetMapping("/updateChecked/{skuId}/{checked}")
    public void updateChecked(@PathVariable(value = "skuId") String skuId,
                              @PathVariable(value = "checked") boolean checked) {
        //1. 获取当前登录用户的用户名
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");
        //2. 调用service更新状态
        cartService.updateChecked(skuId, checked, username);
    }
}
