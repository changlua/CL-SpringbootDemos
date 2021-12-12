package com.changlu.springboot.domain.Basic;

import com.changlu.springboot.Exception.CommonEnum;
import lombok.Data;

/**
 * @ClassName ResultBody
 * @Author ChangLu
 * @Date 2021/9/21 9:55
 * @Description 响应体封装
 */
@Data
public class ResultBody {

    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private Object result;

    public ResultBody() {
    }

    /**
     * 内部封装
     * @param code
     * @param message
     */
    private ResultBody(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 响应码与响应结果封装
     */
    public ResultBody(BaseExceptionInfoInterface baseErrorInfoInterface) {
        this.code = baseErrorInfoInterface.getResultCode();
        this.message = baseErrorInfoInterface.getResultMsg();
    }

    /**
     * 成功
     * @param data 数据
     * @return ResultBody
     */
    public static ResultBody success(Object data){
        ResultBody resultBody = new ResultBody(CommonEnum.SUCCESS);
        resultBody.setResult(data);
        return resultBody;
    }

    /**
     * 错误
     * @param baseErrorInfoInterface 枚举类
     * @return ResultBody
     */
    public static ResultBody fail(BaseExceptionInfoInterface baseErrorInfoInterface){
        ResultBody resultBody = new ResultBody(baseErrorInfoInterface);
        resultBody.setResult(null);
        return resultBody;
    }

    /**
     * 可自定义错误描述，
     * @param baseErrorInfoInterface
     * @param errMsg
     * @return
     */
    public static ResultBody fail(BaseExceptionInfoInterface baseErrorInfoInterface,String errMsg){
        ResultBody resultBody = fail(baseErrorInfoInterface);
        resultBody.setMessage(errMsg);
        return resultBody;
    }


    /**
     * 错误
     * @param code 状态码
     * @param message 描述信息
     * @return ResultBody
     */
    public static ResultBody fail(Integer code,String message){
        ResultBody resultBody = new ResultBody(code,message);
        resultBody.setResult(null);
        return resultBody;
    }


}