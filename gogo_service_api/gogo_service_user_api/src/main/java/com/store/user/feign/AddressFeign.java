package com.store.user.feign;



import com.store.user.pojo.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "user")
@RequestMapping("/address")
public interface AddressFeign {

    /**
     * 功能描述: <br>
     * 〈根据用户名获取收货人地址里列表〉
     *
     * @Param: []
     * @return: java.util.List<com.store.user.pojo.Address>
     * @Author: xiaozhang666
     * @Date: 2020/11/11 20:00
     */
    @GetMapping("/list")
    public List<Address> findListByUserName();
}
