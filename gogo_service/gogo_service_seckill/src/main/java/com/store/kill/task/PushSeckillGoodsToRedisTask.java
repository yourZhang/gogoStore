package com.store.kill.task;

import com.store.kill.mapper.SeckillGoodsMapper;
import com.store.kill.pojo.SeckillGoods;
import com.store.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 定时任务
 * 将数据库中的秒杀商品查询出来, 加载到redis中
 */
@Component
public class PushSeckillGoodsToRedisTask {

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 定时任务30分钟运行一次: 0 0/30 * * * ?
     * 现在测试阶段每30秒执行一次: 0/30 * * * * ?
     *
     * 存入redis中的秒杀商品数据格式例如:
     * key(秒杀时间)                value (hash格式)
     * 2020111608                  秒杀商品id作为小key     秒杀商品对象作为value
     *                             秒杀商品id作为小key     秒杀商品对象作为value
     * 2020111610                  秒杀商品id作为小key     秒杀商品对象作为value
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void task() {
        /**
         * 1. 加载秒杀时间菜单, 以当前时间菜单为准加载最近的5个时间菜单.
         *   两个小时为一个秒杀场次, 一天最多开12个秒杀场次
         */
        List<Date> dateMenus = DateUtil.getDateMenus();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        /**
         * 2. 遍历时间菜单
         */
        for (Date dateMenu : dateMenus) {
            //3. 以时间菜单作为存入redis中秒杀商品数据的key,例如: 2020111608
            String redisKey = DateUtil.date2Str(dateMenu);

            //4. 查询秒杀商品表, 以秒杀时间作为查询条件
            Example example = new Example(SeckillGoods.class);
            Example.Criteria criteria = example.createCriteria();
            //商品审核状态为1审核通过
            criteria.andEqualTo("status", "1");
            //剩余库存量大于0
            criteria.andGreaterThan("stockCount", 0);
            //秒杀开始时间 >= 时间菜单的起始时间
            String startTime = sdf.format(dateMenu);
            criteria.andGreaterThanOrEqualTo("startTime", startTime);

            //秒杀结束时间 <= 时间菜单的结束时间
            Date endTime = DateUtil.addDateHour(dateMenu, 2);
            criteria.andLessThanOrEqualTo("endTime", sdf.format(endTime));

            //5. 查询秒杀商品表, 设置redis中已经加载的秒杀商品数据, 不应该再被查询到不应该再次加载
            Set<String> seckillGoodsIds = redisTemplate.boundHashOps(redisKey).keys();
            if (seckillGoodsIds != null && seckillGoodsIds.size() > 0) {
                criteria.andNotIn("id", seckillGoodsIds);
            }
            //6. 查询并返回结果
            List<SeckillGoods> seckillGoodsList = seckillGoodsMapper.selectByExample(example);

            //7. 遍历查询到的秒杀商品数据
            if (seckillGoodsList != null && seckillGoodsList.size() > 0) {
                for (SeckillGoods seckillGoods : seckillGoodsList) {
                    //8. 将秒杀商品数据放入redis中缓存
                    redisTemplate.boundHashOps(redisKey).put(String.valueOf(seckillGoods.getId()), seckillGoods);

                    /**
                     * 9. 将秒杀商品id作为key, 将秒杀商品剩余库存量作为value存入redis中,
                     *    后期做扣减库存使用redis锁. 防止超卖.
                     */
                    redisTemplate.opsForValue().set(String.valueOf(seckillGoods.getId()), seckillGoods.getStockCount());
                }

            }
        }



    }
}
