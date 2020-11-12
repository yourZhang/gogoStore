package com.store.order.controller;


import com.github.pagehelper.Page;
import com.store.entity.PageResult;
import com.store.entity.Result;
import com.store.entity.StatusCode;
import com.store.order.config.TokenDecode;
import com.store.order.pojo.Order;
import com.store.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private TokenDecode tokenDecode;

    /**
     * 查询全部数据
     * @return
     */
    @GetMapping
    public Result findAll(){
        List<Order> orderList = orderService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",orderList) ;
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id){
        Order order = orderService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",order);
    }


    /***
     * 新增数据
     * @param order
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Order order){
        //1. 获取当前登录用户的用户名
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");
        //2. 调用service保存提交订单
        order.setUsername(username);
        orderService.add(order);
        return new Result(true,StatusCode.OK,"添加成功");
    }


    /***
     * 修改数据
     * @param order
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Order order,@PathVariable String id){
        order.setId(id);
        orderService.update(order);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        orderService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search" )
    public Result findList(@RequestParam Map searchMap){
        List<Order> list = orderService.findList(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }


    /***
     * 分页搜索实现
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result findPage(@RequestParam Map searchMap, @PathVariable  int page, @PathVariable  int size){
        Page<Order> pageList = orderService.findPage(searchMap, page, size);
        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }


}
