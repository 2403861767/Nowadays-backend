package com.seeleaf.common;

/**
 * 返回工具类
 *
 * @author seeleaf
 */
public class ResultUtils {
    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }
    public static BaseResponse error(int code,String message, String description ){
        return new BaseResponse(code,null, message, description);
    }
    public static BaseResponse error(ErrorCode errorCode,String message, String description ){
        return new BaseResponse(errorCode.getCode(),null, message, description);
    }
    public static BaseResponse error(ErrorCode errorCode,String description ){
        return new BaseResponse(errorCode.getCode(), errorCode.getMessage(), description);
    }
}