package com.store.kill.controller;


import com.store.kill.pojo.SeckillGoods;
import com.store.kill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 秒杀商品controller
 */
@RestController
@RequestMapping("/seckillgoods")
public class SeckillGoodsController {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    /**
     * 根据秒杀时间菜单, 获取秒杀商品集合数据返回
     * @param time 秒杀时间菜单, 例如: 2020111608
     * @return
     */
    @GetMapping("/list/{time}")
    public List<SeckillGoods> list(@PathVariable(value = "time") String time) {
        List<SeckillGoods> list = seckillGoodsService.list(time);
        return list;
    }
}
