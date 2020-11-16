package com.store.kill.service;


import com.store.kill.pojo.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SeckillGoods> list(String time) {
        //1. 到redis中根据秒杀时间菜单, 将秒杀商品数据集合查询到
        List<SeckillGoods> seckillGoodsList = redisTemplate.boundHashOps(time).values();
        //2. 遍历redis中的秒杀商品集合数据
        if (seckillGoodsList != null) {
            for (SeckillGoods seckillGoods : seckillGoodsList) {
                //3. 根据秒杀商品id, 到redis中获取秒杀商品最新剩余库存数据
                Object objStockCount = redisTemplate.opsForValue().get(String.valueOf(seckillGoods.getId()));
                if (objStockCount == null) {
                    objStockCount = "0";
                }
                int stockCount = Integer.parseInt(String.valueOf(objStockCount));
                //4. 将秒杀商品最新剩余库存数据, 放入秒杀商品对象中, 返回
                seckillGoods.setStockCount(stockCount);
            }
        }

        return seckillGoodsList;
    }
}
