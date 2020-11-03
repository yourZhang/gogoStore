
package com.store.feign;

import com.store.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@FeignClient("goods")
@RequestMapping("/sku")
public interface SkuFeign {

    /**
     * 功能描述: <br>
     * 〈查询所有sku数据〉
     *
     * @Param: []
     * @return: java.util.List<com.store.pojo.Sku>
     * @Author: xiaozhang666
     * @Date: 2020/11/2 19:56
     */
    @GetMapping("listAll")
    List<Sku> findAllList();


    /**
     * 功能描述: <br>
     * 〈根据id更新es中上架状态〉
     *
     * @Param: [spuId]
     * @return: java.util.List<com.store.pojo.Sku>
     * @Author: xiaozhang666
     * @Date: 2020/11/2 19:57
     */
    @GetMapping("findBySpuId/{spuId}")
    List<Sku> findBySpuId(@PathVariable("spuId") String spuId);
}
