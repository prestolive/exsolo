package cn.exsolo.springmvcext.in.out;

import org.springframework.lang.NonNull;

public class BaseResponse<T> {

    private Integer code;

    private String message;

    private T data;

    public BaseResponse(Integer status, String message) {
        this.code = status;
        this.message = message;
    }

    public BaseResponse(Integer status, String message, T data) {
        this.code = status;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> ok(@NonNull T data) {
        return new BaseResponse<>(0,"", data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}