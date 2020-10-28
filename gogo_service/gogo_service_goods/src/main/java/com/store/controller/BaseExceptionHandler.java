package com.store.controller;

import com.store.entity.Result;
import com.store.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {

    /**
     * 功能描述: <br>
     * 〈统一异常处理〉
     *
     * @Param: [e]
     * @return: com.store.entity.Result
     * @Author: xiaozhang666
     * @Date: 2020/10/28 17:35
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
