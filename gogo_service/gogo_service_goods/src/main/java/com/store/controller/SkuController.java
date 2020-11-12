package com.store.controller;

import com.github.pagehelper.Page;
import com.store.entity.PageResult;
import com.store.entity.Result;
import com.store.entity.StatusCode;
import com.store.pojo.Sku;
import com.store.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/sku")
public class SkuController {


    @Autowired
    private SkuService skuService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Sku> skuList = skuService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", skuList);
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        Sku sku = skuService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", sku);
    }

    @GetMapping("/one/{id}")
    public Sku findOneById(@PathVariable String id) {
        return skuService.findById(id);
    }

    /***
     * 新增数据
     * @param sku
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Sku sku) {
        skuService.add(sku);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    /***
     * 修改数据
     * @param sku
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Sku sku, @PathVariable String id) {
        sku.setId(id);
        skuService.update(sku);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        skuService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search")
    public Result findList(@RequestParam Map searchMap) {
        List<Sku> list = skuService.findList(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }


    /***
     * 分页搜索实现
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result findPage(@RequestParam Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Sku> pageList = skuService.findPage(searchMap, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 功能描述: <br>
     * 〈查询全部数据--副本〉
     *
     * @Param: []
     * @return: java.util.List<com.store.pojo.Sku>
     * @Author: xiaozhang666
     * @Date: 2020/11/2 19:44
     */
    @GetMapping("listAll")
    public List<Sku> findAllList() {
        return skuService.findAllList();
    }

    /**
     * 功能描述: <br>
     * 〈更新数据同步〉
     *
     * @Param: [spuId]
     * @return: java.util.List<com.store.pojo.Sku>
     * @Author: xiaozhang666
     * @Date: 2020/11/2 21:00
     */
    @GetMapping("findBySpuId/{spuId}")
    public List<Sku> findBySpuId(@PathVariable("spuId") String spuId) {
        return skuService.findBySpuId(spuId);
    }

    /**
     * 功能描述: <br>
     * 〈扣减库存, 增加销量〉
     *
     * @Param: [skuId, num]
     * @return: void
     * @Author: xiaozhang666
     * @Date: 2020/11/12 15:09
     */
    @GetMapping("/decrCount/{skuId}/{num}")
    public void decrCount(@PathVariable(value = "skuId") String skuId, @PathVariable(value = "num") Integer num) {
        skuService.decrCount(skuId, num);
    }

    /**
     * 恢复库存和销量
     *
     * @param skuId
     * @param num
     */
    @GetMapping("/incrCount/{skuId}/{num}")
    public void incrCount(@PathVariable(value = "skuId") String skuId, @PathVariable(value = "num") Integer num) {
        skuService.incrCount(skuId, num);
    }

}
