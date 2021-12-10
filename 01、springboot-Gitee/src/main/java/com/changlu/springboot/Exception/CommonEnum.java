package com.changlu.springboot.Exception;


import com.changlu.springboot.domain.Basic.BaseExceptionInfoInterface;

/**
 *  常见异常枚举类
 * @author changlu
 * @date 2021/07/22 17:05
 **/
public enum CommonEnum implements BaseExceptionInfoInterface {

    //成功情况
    SUCCESS(200,"成功！"),
    //图床异常处理
    IMAGE_EXIST_ERROR(1001, "服务器未接收到上传资源"),
    IMAGE_UPLOAD_ERROR(1002,"上传Gitee图床失败"),
    DEL_FILE_FAILED(1003,"图片删除失败"),
    URL_PARSE_FAILED(1004,"Gitee图片url无法解析"),

    //系统异常
    SYSTEM_ERROR(2000, "系统异常，请从控制台或日志中查看具体错误信息");

    //错误码
    private final Integer resultCode;

    //描述信息
    private final String resultMsg;

    CommonEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public Integer getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}
