package com.changlu.springboot.Exception;


/**
 * @ClassName OwnException
 * @Author ChangLu
 * @Date 2021/7/29 23:40
 * @Description 自定义异常类
 */
public class OwnException extends RuntimeException{

    private final Integer code;
    private final String message;

    public OwnException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public OwnException(CommonEnum ex){
        this(ex.getResultCode(),ex.getResultMsg());
    }

    public OwnException(CommonEnum ex, String msg){
        this(ex.getResultCode(),msg);
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}