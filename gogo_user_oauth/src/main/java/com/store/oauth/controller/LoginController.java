package com.store.oauth.controller;


import com.store.oauth.service.AuthService;
import com.store.oauth.util.AuthToken;
import com.store.oauth.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录认证controller
 */
@Controller
@RequestMapping("/oauth")
public class LoginController {

    @Autowired
    private AuthService authService;

    //客户端id
    @Value("${auth.clientId}")
    private String clientId;

    //客户端秘钥
    @Value("${auth.clientSecret}")
    private String clientSecret;

    //jti放到cookie中的域名
    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    //jti放到cookie中的生存时间
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    @Autowired
    HttpServletResponse response;


    /**
     * 跳转到登录页面
     *
     * @return
     */
    @GetMapping("/toLogin")
    public String toLogin(@RequestParam(value = "ReturnUrl", defaultValue = "http://62.234.118.219/xiaozhang666/") String ReturnUrl, Model model) {
        model.addAttribute("ReturnUrl", ReturnUrl);
        return "login";
    }

    /**
     * 功能描述: <br>
     * @param ReturnUrl 登录成功后跳转的页面地址
     * @param username  消费者用户名
     * @param password  消费者密码
     *
     * @Param: [ReturnUrl, username, password, model]
     * @return: java.lang.String
     * @Author: xiaozhang666
     * @Date: 2020/11/9 17:21
     */
    @PostMapping("/login")
    public String login(String ReturnUrl, String username, String password, Model model) {
        //1. 判断用户名是否为空, 如果为空跳转到登录页面重新登录
        if (StringUtils.isEmpty(username)) {
            model.addAttribute("ReturnUrl", ReturnUrl);
            return "login";
        }
        //2. 判断密码是否为空, 如果为空跳转到登录页面重新登录
        if (StringUtils.isEmpty(password)) {
            model.addAttribute("ReturnUrl", ReturnUrl);
            return "login";
        }
        //3. 调用service的登录方法, 传入消费者用户名密码, 传入客户端id和秘钥
        AuthToken authToken = authService.login(username, password, clientId, clientSecret);

        //4. 登录成功后会返回jti短令牌和jwt长令牌, 将jti短令牌存入消费者浏览器cookie中
        if (authToken != null) {
            CookieUtil.addCookie(response, cookieDomain, "/", "jti", authToken.getJti(), cookieMaxAge, false);
        }
        return "redirect:" + ReturnUrl;
    }
}
