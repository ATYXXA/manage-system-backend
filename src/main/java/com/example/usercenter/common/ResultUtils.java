package com.example.usercenter.common;

/**
 * 返回工具类
 */
public class ResultUtils {

    public static <T> BaseResponse<T> success(T data){

        return new BaseResponse<>(0,data,"ok");
    }

    /**
     * 返回默认状态码
     * @param errorCode
     * @return 默认的状态码
     */
    public static BaseResponse error(ErrorCode errorCode){

        return new BaseResponse<>(errorCode);//todo 没搞懂这里的<>符号有啥用
    }

    /**
     * 自定义
     * @param code
     * @param message
     * @param desciption
     * @return
     */
    public static BaseResponse error(int code,String message, String desciption){
        return new BaseResponse(code,null,message,desciption);
    }

    /**
     * 更改状态码的message和desciption
     * @param errorCode
     * @param message
     * @param desciption
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String message, String desciption){
        return new BaseResponse(errorCode.getCode(),null,message,desciption);
    }

    /**
     * 更改错误码的description
     * @param errorCode
     * @param desciption
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String desciption){
        return new BaseResponse(errorCode.getCode(),null,errorCode.getMessage(),desciption);
    }
}
