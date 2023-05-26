package cn.exsolo.springmvcext.in.out;

import org.springframework.lang.NonNull;

public class BaseResponse<T> {

    /**
     * 0 成功 -1 失败 -2限流
     */
    private Integer code;

    private String errcode;
    private String errmsg;

    private T data;

    public BaseResponse(Integer status, String errcode, String errmsg) {
        this.code = status;
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public BaseResponse(Integer status, String errcode, String errmsg, T data) {
        this.code = status;
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.data = data;
    }

//    public static <T> BaseResponse<T> ok(@NonNull T data) {
//        return new BaseResponse<>(0, null,null, data);
//    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}