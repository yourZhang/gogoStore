package com.store.page.controller;

import com.store.entity.Result;
import com.store.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: gogo-store
 * @description: PageController
 * @author: xiaozhang6666
 * @create: 2020-11-05 15:38
 **/

@RestController
@RequestMapping("page")
public class PageController {

    @Autowired
    PageService pageService;

    /**
     * 功能描述: <br>
     * 〈生成页面〉
     *
     * @Param: [spuId]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/11/5 15:50
     */
    @RequestMapping(value = "createHtml/{spuId}", method = RequestMethod.GET)
    public Result createHtml(@PathVariable String spuId) {
        pageService.createHtml(spuId);
        return new Result("生成详情页成功...");
    }
}
