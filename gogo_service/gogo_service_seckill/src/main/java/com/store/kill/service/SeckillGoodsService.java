package com.store.kill.service;


import com.store.kill.pojo.SeckillGoods;
import java.util.List;

public interface SeckillGoodsService {

    /**
     * 获取秒杀商品集合数据返回
     * @param time
     * @return
     */
    public List<SeckillGoods> list(String time);
}
