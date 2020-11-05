
package com.store.feign;


import com.store.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("goods")
@RequestMapping("/category")
public interface CategoryFeign {

    @GetMapping("/{id}")
    Category findById(@PathVariable("id") Integer id);
}
