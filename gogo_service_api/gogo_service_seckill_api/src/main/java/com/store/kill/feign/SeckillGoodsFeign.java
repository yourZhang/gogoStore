package com.store.kill.feign;


import com.store.kill.pojo.SeckillGoods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 *
 */
@FeignClient(name = "seckill")
@RequestMapping("/seckillgoods")
public interface SeckillGoodsFeign {

    /**
     * 根据秒杀时间菜单, 获取秒杀商品集合数据返回
     * @param time 秒杀时间菜单, 例如: 2020111608
     * @return
     */
    @GetMapping("/list/{time}")
    public List<SeckillGoods> list(@PathVariable(value = "time") String time);
}
