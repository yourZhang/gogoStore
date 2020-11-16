package com.store.kill.controller;


import com.store.entity.Constants;
import com.store.entity.Result;
import com.store.entity.StatusCode;
import com.store.kill.util.CookieUtil;
import com.store.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀下单controller
 */
@Controller
@RequestMapping("/wseckillorder")
public class WebSeckillOrderController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取页面令牌
     * @return
     */
    @GetMapping("/getToken")
    @ResponseBody
    public String getToken() {
        //1. 获取用户浏览器中cookie中的jti短令牌
        Map<String, String> cookieMap = CookieUtil.readCookie(request, "jti");
        String jti = cookieMap.get("jti");

        //2. 判断是否能够获取到jti短令牌,
        if (StringUtils.isEmpty(jti)) {
            //3. 如果获取不到直接抛异常
            throw new RuntimeException("非法用户访问!");
        } else {
            //4. 如果获取到jti短令牌, 生成一个随机数作为页面令牌token
            String token = RandomUtil.getRandomString();
            //5. 将jti短令牌作为key, 页面令牌token作为value, 存入redis中, 时间3-5秒
            redisTemplate.boundValueOps(Constants.PAGE_TOKEN + jti).set(token, 3, TimeUnit.SECONDS);
            //6. 将token页面令牌返回给页面浏览器
            return token;
        }
    }

    /**
     * 秒杀下单
     * @param time  秒杀商品的时间菜单, 例如: 2020111610
     * @param id    秒杀商品id
     * @param token 页面令牌
     * @return
     */
    @GetMapping("/add")
    @ResponseBody
    public Result add(String time, String id, String token) {
        //1. 判断消费者传入的页面令牌是否为空, 为空不允许下单
        if (StringUtils.isEmpty(token)) {
            return new Result(false, StatusCode.ERROR, "非法访问不允许下单!");
        }
        //2. 获取用户浏览器cookie中的短令牌jti
        Map<String, String> cookieMap = CookieUtil.readCookie(request, "jti");
        String jti = cookieMap.get("jti");

        //3. 判断jti短令牌如果为空, 不允许下单
        if (StringUtils.isEmpty(jti)) {
            return new Result(false, StatusCode.ERROR, "非法访问不允许下单!");
        }
        //4. 根据jti短令牌到redis中, 获取redis中存储的页面令牌
        Object objRedisToken = redisTemplate.boundValueOps(Constants.PAGE_TOKEN + jti).get();

        //5. 判断redis中存储的页面令牌如果为空不允许下单
        if (objRedisToken == null) {
            return new Result(false, StatusCode.ERROR, "非法访问不允许下单!");
        }

        //6. 判断消费者传入的页面令牌和redis中存储的页面令牌如果一致允许下单
        if (token.equals(String.valueOf(objRedisToken))) {
            //todo 通过feign远程调用秒杀业务微服务下单接口

            return new Result(true, StatusCode.OK, "下单成功!");
        }
        //7. 其他情况都不允许下单
        return new Result(false, StatusCode.ERROR, "非法访问不允许下单!");
    }
}
