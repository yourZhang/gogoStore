package com.store.user.feign;

import com.store.entity.Result;
import com.store.entity.StatusCode;
import com.store.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient("user")
@RequestMapping("/user")
public interface UserFeign {

    @GetMapping("/{username}")
    public Result findById(@PathVariable("username") String username);

    @GetMapping("/ones/{username}")
    public User findByIdOne(@PathVariable("username") String username);
}
