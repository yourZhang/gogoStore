package com.store.controller;

import com.store.entity.Result;
import com.store.entity.StatusCode;
import com.store.pojo.Album;
import com.store.service.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: gogo-store
 * @description: 相册
 * @author: xiaozhang6666
 * @create: 2020-10-27 16:38
 **/
@RestController
@RequestMapping("album")
public class AlbumController {

    private final  Logger logger = LoggerFactory.getLogger(AlbumController.class);

    @Autowired
    AlbumService albumService;

    /**
     * 功能描述: <br>
     * 〈查询所有〉
     *
     * @Param: []
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/27 16:50
     */
    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    public Result findAll() {
        final List<Album> all = albumService.findAll();
        return new Result(true, StatusCode.OK, "查询相册所有", all);
    }

    /**
     * 功能描述: <br>
     * 〈添加下单个相册〉
     *
     * @Param: [album]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/28 17:08
     */
    @RequestMapping("add")
    public Result add(@RequestBody Album album) {
        final Integer add = albumService.add(album);
        return new Result(true, StatusCode.OK, "新增条数", add);
    }
}
