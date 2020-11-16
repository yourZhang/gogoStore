package com.store.kill.controller;

import com.store.entity.Result;
import com.store.entity.StatusCode;
import com.store.kill.feign.SeckillGoodsFeign;
import com.store.kill.pojo.SeckillGoods;
import com.store.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 秒杀商品controller
 */
@Controller
@RequestMapping("/wseckillgoods")
public class WebSeckillGoodsController {

    @Autowired
    private SeckillGoodsFeign seckillGoodsFeign;

    /**
     * 跳转到秒杀首页
     *
     * @return
     */
    @GetMapping("/toIndex")
    public String toIndex() {
        return "seckill-index";
    }

    /**
     * 功能描述: <br>
     * 〈获取秒杀首页, 时间菜单数据返回〉
     *
     * @Param: []
     * @return: java.util.List<java.lang.String>
     * @Author: xiaozhang666
     * @Date: 2020/11/16 16:58
     */
    @GetMapping("/timeMenus")
    @ResponseBody
    public List<String> timeMenus() {
        List<Date> dateMenus = DateUtil.getDateMenus();
        List<String> resultList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Date dateMenu : dateMenus) {
            resultList.add(sdf.format(dateMenu));
        }
        return resultList;
    }

    /**
     * 根据时间菜单获取秒杀商品集合数据返回
     *
     * @param time 秒杀菜单时间, 例如: 2020-11-16 10:00:00
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Result<List<SeckillGoods>> list(String time) {
        String redisKey = DateUtil.formatStr(time);
        //通过feign远程调用秒杀业务微服务获取秒杀商品集合数据返回
        List<SeckillGoods> list = seckillGoodsFeign.list(redisKey);
        return new Result<>(true, StatusCode.OK, "获取数据成功", list);
    }
}
