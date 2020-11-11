package com.store.user.controller;


import com.github.pagehelper.Page;
import com.store.entity.PageResult;
import com.store.entity.Result;
import com.store.entity.StatusCode;
import com.store.user.config.TokenDecode;
import com.store.user.pojo.Address;
import com.store.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/address")
public class AddressController {


    @Autowired
    private AddressService addressService;

    @Autowired
    private TokenDecode tokenDecode;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Address> addressList = addressService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", addressList);
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        Address address = addressService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", address);
    }


    /***
     * 新增数据
     * @param address
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Address address) {
        addressService.add(address);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    /***
     * 修改数据
     * @param address
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Address address, @PathVariable Integer id) {
        address.setId(id);
        addressService.update(address);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        addressService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search")
    public Result findList(@RequestParam Map searchMap) {
        List<Address> list = addressService.findList(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }


    /***
     * 分页搜索实现
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result findPage(@RequestParam Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Address> pageList = addressService.findPage(searchMap, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 功能描述: <br>
     * 〈获取用户地址列表〉
     *
     * @Param: []
     * @return: java.util.List<com.store.user.pojo.Address>
     * @Author: xiaozhang666
     * @Date: 2020/11/11 20:18
     */
    @GetMapping("/list")
    public List<Address> findListByUserName() {
        //1. 获取消费者登录用户的用户名
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("username");
        //2. 根据用户名作为条件查询收货人地址列表
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        List<Address> list = addressService.findList(paramMap);
        //3. 返回数据
        return list;
    }
}
