package com.changlu.springboot.handler;

import com.changlu.springboot.Exception.CommonEnum;
import com.changlu.springboot.Exception.OwnException;
import com.changlu.springboot.domain.Basic.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName GlobalExceptionHandler
 * @Author ChangLu
 * @Date 2021/12/11 0:04
 * @Description TODO
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理全局异常(Exception)
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultBody handleException(HttpServletRequest request, Exception e){
        log.error("Exception:",e);
        return ResultBody.fail(CommonEnum.SYSTEM_ERROR);
    }

    /**
     * 自定义异常(可自行抛出针对于一些受检类型，继承RuntimeException)
     * @param ex 自定义抛出异常
     * @return xyz.changlu.util.ResultBody
     */
    @ExceptionHandler(value = OwnException.class)
    public ResultBody msgExceptionHandler(HttpServletRequest request, OwnException ex){
        log.error("OwnException:",ex);
        return ResultBody.fail(ex.getCode(),ex.getMessage());
    }

}