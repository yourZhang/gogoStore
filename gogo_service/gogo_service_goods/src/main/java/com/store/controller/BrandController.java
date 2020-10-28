package com.store.controller;

import com.github.pagehelper.Page;
import com.store.entity.PageResult;
import com.store.entity.Result;
import com.store.entity.StatusCode;
import com.store.pojo.Brand;
import com.store.service.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: gogo-store
 * @description: BrandController
 * @author: xiaozhang6666
 * @create: 2020-10-27 11:30
 **/
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;
    private final Logger logger = LoggerFactory.getLogger(BrandController.class);

    /**
     * 功能描述: <br>
     * 〈查询所有〉
     *
     * @Param: []
     * @return:
     * @Author: xiaozhang666
     * @Date: 2020/10/27 12:21
     */
    @RequestMapping("findAll")
    public Result findAll() {
        final List<Brand> all = brandService.findAll();
        return new Result(true, 200, "成功", all);
    }

    /**
     * 功能描述: <br>
     * 〈根据id查询〉
     *
     * @Param: [id]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/27 12:28
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable Integer id) {
        logger.info("{}==================================", id);
        final Brand byId = brandService.findById(id);
        return new Result(true, StatusCode.OK, "成功", byId);
    }

    /**
     * 功能描述: <br>
     * 〈添加单条〉
     *
     * @Param: [brand]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/27 12:56
     */
    @RequestMapping("add")
    public Result add(@RequestBody Brand brand) {
        final Integer add = brandService.add(brand);
        return new Result(true, StatusCode.OK, "成功", add);
    }

    /**
     * 功能描述: <br>
     * 〈更新一条〉
     *
     * @Param: [brand]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/27 13:04
     */
    @RequestMapping("update")
    public Result update(@RequestBody Brand brand) {
        final Integer update = brandService.update(brand);
        return new Result(true, StatusCode.OK, "成功", update);
    }

    /**
     * 功能描述: <br>
     * 〈根据id删除〉
     *
     * @Param: [id]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/27 13:52
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Result del(@PathVariable Integer id) {
        final Integer del = brandService.del(id);
        return new Result(true, StatusCode.OK, "成功", del);
    }

    /**
     * 功能描述: <br>
     * 〈条件查询〉
     *
     * @Param: [params]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/27 14:44
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public Result search(@RequestParam Map<String, String> params) {
        System.out.println(params.get("name"));
        final List<Brand> search = brandService.search(params);
        System.out.println(search);
        return new Result(true, StatusCode.OK, "成功", search);
    }

    /**
     * 功能描述: <br>
     * 〈分页查询〉
     *
     * @Param: [page, pageSize]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/27 14:55
     */
    @RequestMapping("search2/{page}/{pageSize}")
    public Result search2(@PathVariable Integer page, @PathVariable Integer pageSize) {
        final Page<Brand> brands = brandService.search2(page, pageSize);
        return new Result(true, 200, "分页查询", new PageResult(brands.getTotal(), brands.getResult()));
    }

    /**
     * 功能描述: <br>
     * 〈条件查询分页〉
     *
     * @Param: [params, page, pageSize]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/27 15:13
     */
    @RequestMapping(value = "pageList/{page}/{pageSize}", method = RequestMethod.GET)
    public Result pageList(@RequestParam Map<String, String> params, @PathVariable Integer page, @PathVariable Integer pageSize) {
        final Page<Brand> brands = brandService.pageList(params, page, pageSize);
        return new Result(true, StatusCode.OK, "成功", new PageResult<Brand>(brands.getTotal(), brands.getResult()));
    }

    /**
     * 功能描述: <br>
     * 〈根据分类名称查询品牌列表〉
     *
     * @Param: [cateName]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/28 16:10
     */
    @RequestMapping(value = "cate/{cateName}", method = RequestMethod.GET)
    public Result findBrandByCateName(@PathVariable String cateName) {
        List<Brand> brandList = brandService.findBrandByCateName(cateName);
        return new Result(brandList);
    }

}
