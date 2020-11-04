package com.store.search.controller;

import com.store.entity.Result;
import com.store.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @program: gogo-store
 * @description: 搜索接口
 * @author: xiaozhang6666
 * @create: 2020-11-04 15:45
 **/
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    SearchService searchService;

    /**
     * 功能描述: <br>
     * 〈简单搜索功能 -- 〉
     *
     * @Param: [searchMap]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/11/4 15:51
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result getList(@RequestParam Map<String, String> searchMap) {
        final Map<String, Object> list = searchService.getList(searchMap);
        return new Result(list);
    }
}



