package cn.exsolo.springmvcext.in.out;

import cn.exsolo.comm.ex.*;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 全局异常处理类
 * @author prestolive
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    private final static String EX_BIZ_EXCEPTION_ERROR_CODE = "_BIZ_ERROR";

    private final static String EX_DEV_EXCEPTION_ERROR_CODE = "_DEV_ERROR";

    private final static String EX_NEVER_EXCEPTION_ERROR_CODE = "_NEVER_ERROR";

    private final static String EX_UNKNOWN_EXCEPTION_ERROR_CODE = "_UNKNOWN_ERROR";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleGlobalException(Throwable e) {
        e.printStackTrace();
        return new BaseResponse<>(-1,EX_UNKNOWN_EXCEPTION_ERROR_CODE,e.getMessage());
    }

    @ExceptionHandler(ExDevException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleDevException(Throwable e) {
        e.printStackTrace();
        return new BaseResponse<>(-1,EX_DEV_EXCEPTION_ERROR_CODE,e.getMessage());
    }

    @ExceptionHandler(ExNeverException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleNeverException(Throwable e) {
        e.printStackTrace();
        return new BaseResponse<>(-1,EX_NEVER_EXCEPTION_ERROR_CODE,e.getMessage());
    }

    @ExceptionHandler(ExBizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleBizException(Throwable e) {
        e.printStackTrace();
        return new BaseResponse<>(-1,EX_BIZ_EXCEPTION_ERROR_CODE,e.getMessage());
    }

    @ExceptionHandler(ExDeclaredException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleDeclaredException(Throwable e) {
        e.printStackTrace();
        ExDeclaredException exception = (ExDeclaredException) e;
        String errcode= exception.getErrorItem().name();
        String errmsg = formatErrorMessage(exception);
        return new BaseResponse<>(-1,errcode,errmsg,exception.getResponseData());
    }


    /**
     * 从ExDeclaredException 的错误枚举中，默认提取name，用来输出套打后的错误消息。
     * @param exception
     * @return
     */
    private String formatErrorMessage(ExDeclaredException exception){
        try {
            Method method = exception.getErrorItem().getClass().getMethod("getName");
            String messageFmt = (String) method.invoke(exception.getErrorItem());
            String message = String.format(messageFmt,exception.getArgs());
            return message;
        } catch (Exception e) {
            return "unknown error, failed to pick error message.";
        }
    }
}