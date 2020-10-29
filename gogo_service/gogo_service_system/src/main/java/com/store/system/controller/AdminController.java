package com.store.system.controller;

import com.github.pagehelper.Page;
import com.store.entity.PageResult;
import com.store.entity.Result;
import com.store.entity.StatusCode;
import com.store.system.pojo.Admin;
import com.store.system.service.AdminService;
import com.store.system.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Admin> adminList = adminService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", adminList);
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        Admin admin = adminService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", admin);
    }


    /***
     * 新增数据
     * @param admin
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    /***
     * 修改数据
     * @param admin
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Admin admin, @PathVariable Integer id) {
        admin.setId(id);
        adminService.update(admin);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        adminService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search")
    public Result findList(@RequestParam Map searchMap) {
        List<Admin> list = adminService.findList(searchMap);
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
        Page<Admin> pageList = adminService.findPage(searchMap, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    Logger logger = LoggerFactory.getLogger(getClass());

    /*
        用户登录
     */
    @PostMapping("login")
    public Result login(@RequestBody Admin admin, HttpSession session) {
        boolean flag = adminService.login(admin);
        if (flag) {
            Map<String, String> infoMap = new HashMap<>();
            final String KeyId = UUID.randomUUID().toString();
            String jwt = JwtUtil.createJWT(KeyId, admin.getLoginName(), null);
            infoMap.put("token", jwt);
            infoMap.put("loginName", admin.getLoginName());
//            final Enumeration<String> e = session.getAttributeNames();
//            while (e.hasMoreElements()) {
//                String sessionName = (String) e.nextElement();
            logger.info("session item name= {}", session.getAttribute(admin.getLoginName()));
//            }
            return new Result(infoMap);
        } else {
            return new Result("用户名或密码错误");
        }
    }
}
