package com.changlu.springboot.domain.Basic;

/**
 *  基本异常接口
 * @author changlu
 * @date 2021/07/22 17:03
 **/
public interface BaseExceptionInfoInterface {

    /**
     * 得到错误码
     * @date 2021/07/22 17:04
     * @return java.lang.String
     */
    Integer getResultCode();

    /**
     * 得到错误信息
     * @date 2021/07/22 17:05
     * @return java.lang.String
     */
    String getResultMsg();

}
