package com.store.controller;


import com.github.pagehelper.Page;
import com.store.entity.PageResult;
import com.store.entity.Result;
import com.store.entity.StatusCode;
import com.store.pojo.Goods;
import com.store.pojo.Spu;
import com.store.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/spu")
public class SpuController {


    @Autowired
    private SpuService spuService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Spu> spuList = spuService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", spuList);
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable String id) {
        Spu spu = spuService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", spu);
    }


    /***
     * 新增数据
     * @param spu
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Spu spu) {
        spuService.add(spu);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    /***
     * 修改数据
     * @param spu
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Spu spu, @PathVariable String id) {
        spu.setId(id);
        spuService.update(spu);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        spuService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search")
    public Result findList(@RequestParam Map searchMap) {
        List<Spu> list = spuService.findList(searchMap);
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
        Page<Spu> pageList = spuService.findPage(searchMap, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 功能描述: <br>
     * 〈sku和spu插入〉
     *
     * @Param: [goods]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/30 20:29
     */
    @RequestMapping(value = "addGoods", method = RequestMethod.POST)
    public Result addGoods(@RequestBody Goods goods) {
        spuService.addGoods(goods);
        return new Result("商品新增成功");
    }

    /**
     * 功能描述: <br>
     * 〈根据就id查询〉
     *
     * @Param: [spuId]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/30 20:33
     */
    @GetMapping("goods/{spuId}")
    public Result goods(@PathVariable String spuId) {
        Goods goods = spuService.findGoods(spuId);
        return new Result(goods);
    }

    /**
     * 功能描述: <br>
     * 〈修改〉
     *
     * @Param: [goods]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/30 20:41
     */
    @PostMapping("updateGoods")
    public Result updateGoods(@RequestBody Goods goods) {
        spuService.updateGoods(goods);
        return new Result("商品更新成功");
    }

    /**
     * 功能描述: <br>
     * 〈商品审核 status=1 审核通过〉
     *
     * @Param: [spuId]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/30 20:41
     */
    @PostMapping("auditGoods/{spuId}")
    public Result auditGoods(@PathVariable String spuId) {
        spuService.auditGoods(spuId);
        return new Result("商品审核通过");
    }

    /**
     * 功能描述: <br>
     * 〈商品上架〉
     *
     * @Param: [spuId]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/30 20:44
     */
    @PostMapping("upGoods/{spuId}")
    public Result upGoods(@PathVariable String spuId) {
        spuService.upGoods(spuId);
        return new Result("商品上架成功");
    }

    /**
     * 功能描述: <br>
     * 〈下架〉
     *
     * @Param: [spuId]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/30 20:45
     */
    @PostMapping("down/{spuId}")
    public Result down(@PathVariable String spuId) {
        spuService.down(spuId);
        return new Result("商品下架成功");
    }

    /**
     * 功能描述: <br>
     * 〈软删除商品〉
     *
     * @Param: [spuId]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/30 20:48
     */
    @PostMapping("deleteLogic/{spuId}")
    public Result deleteLogic(@PathVariable String spuId) {
        spuService.deleteLogic(spuId);
        return new Result("商品删除成功");
    }

    /**
     * 功能描述: <br>
     * 〈还原状态〉
     *
     * @Param: [spuId]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/30 20:50
     */
    @PostMapping("restore/{spuId}")
    public Result restore(@PathVariable String spuId) {
        spuService.restore(spuId);
        return new Result("商品还原成功");
    }

    /**
     * 功能描述: <br>
     * 〈硬删除〉
     *
     * @Param: [spuId]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/30 20:54
     */
    @PostMapping("deleteGoods/{spuId}")
    public Result deleteGoods(@PathVariable String spuId) {
        spuService.deleteGoods(spuId);
        return new Result("商品物理删除成功");
    }
}
