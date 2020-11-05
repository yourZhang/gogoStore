
package com.store.feign;

import com.store.pojo.Spu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("goods")
@RequestMapping("/spu")
public interface SpuFeign {

    @GetMapping("findOne/{id}")
    Spu findByIds(@PathVariable("id") String id);
}
