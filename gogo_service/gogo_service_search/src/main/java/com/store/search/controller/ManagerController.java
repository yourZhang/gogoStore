
package com.store.search.controller;

import com.store.entity.Result;
import com.store.search.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("manage")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    /**
     * 功能描述: <br>
     * 〈导入历史数据〉
     *
     * @Param: []
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/11/2 20:10
     */
    @PostMapping("importAll")
    public Result importAll() {
        managerService.importAll();
        return new Result("导入全量数据成功...");
    }
}
