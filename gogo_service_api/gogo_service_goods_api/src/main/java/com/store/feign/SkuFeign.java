
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

    /**
     * @Author: guodong
     * @Date: 14:38 2020/11/2
     * @Parms []
     * @ReturnType: java.util.List<com.changgou.goods.pojo.Sku>
     * @Description: 查询所有sku数据
     */
    @GetMapping
    List<Sku> findAll();

    /**
     * 功能描述: <br>
     * 〈根据id查询数据〉
     *
     * @Param: [id]
     * @return: com.store.pojo.Sku
     * @Author: xiaozhang666
     * @Date: 2020/11/10 14:10
     */
    @GetMapping("/one/{id}")
    public Sku findOneById(@PathVariable(value = "id") String id);

    /**
     * 功能描述: <br>
     * 〈扣减库存, 增加销量〉
     *
     * @Param: [skuId, num]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/12 15:08
     */
    @GetMapping("/decrCount/{skuId}/{num}")
    public void decrCount(@PathVariable(value = "skuId") String skuId, @PathVariable(value = "num") Integer num);

    /**
     * 功能描述: <br>
     * 〈恢复库存和销量〉
     *
     * @Param: [skuId, num]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/12 15:08
     */
    @GetMapping("/incrCount/{skuId}/{num}")
    public void incrCount(@PathVariable(value = "skuId") String skuId, @PathVariable(value = "num") Integer num);
}
