package cn.exsolo.springmvcext.in.out;

import cn.exsolo.batis.core.utils.GenerateID;
import cn.exsolo.comm.ex.*;
import cn.exsolo.kit.DevKitSettingProvider;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 *
 * @author prestolive
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    private final static String EX_BIZ_EXCEPTION_ERROR_CODE = "_BIZ_ERROR";

    private final static String EX_DEV_EXCEPTION_ERROR_CODE = "_DEV_ERROR";

    private final static String EX_NEVER_EXCEPTION_ERROR_CODE = "_NEVER_ERROR";

    private final static String EX_UNKNOWN_EXCEPTION_ERROR_CODE = "_UNKNOWN_ERROR";

    private final static Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleGlobalException(Throwable e) {
        return commonErrorMessageProcess(e,EX_UNKNOWN_EXCEPTION_ERROR_CODE);
    }

    @ExceptionHandler(ExDevException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleDevException(Throwable e) {
        return commonErrorMessageProcess(e,EX_DEV_EXCEPTION_ERROR_CODE);
    }

    @ExceptionHandler(ExNeverException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleNeverException(Throwable e) {
        return commonErrorMessageProcess(e,EX_NEVER_EXCEPTION_ERROR_CODE);
    }

    @ExceptionHandler(ExBizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleBizException(Throwable e) {
        return commonErrorMessageProcess(e,EX_BIZ_EXCEPTION_ERROR_CODE);
    }

    @ExceptionHandler(ExDeclaredException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleDeclaredException(Throwable e) {
        String traceId = GenerateID.next();
        ExDeclaredException exception = (ExDeclaredException) e;
        String errcode = exception.getErrorItem().name();
        String errmsg = String.format("traceId:%s,message:%s",traceId,formatErrorMessage(exception));
        log.error(errmsg,e);
        BaseResponse resp = new BaseResponse<>(-1, errcode, errmsg, exception.getResponseData());
        if(DevKitSettingProvider.IS_ALLOW_WEB_ERROR_STACK){
            fillStack(resp,e);
        }
        return resp;
    }

    private BaseResponse<?> commonErrorMessageProcess(Throwable e,String errcode){
        String traceId = GenerateID.next();
        String errmsg = String.format("traceId:%s,message:%s",traceId,e.getMessage());
        log.error(errmsg,e);
        BaseResponse resp = new BaseResponse<>(-1, errcode, errmsg);
        if(DevKitSettingProvider.IS_ALLOW_WEB_ERROR_STACK){
            fillStack(resp,e);
        }
        return resp;
    }


    /**
     * 从ExDeclaredException 的错误枚举中，默认提取name，用来输出套打后的错误消息。
     *
     * @param exception
     * @return
     */
    private String formatErrorMessage(ExDeclaredException exception) {
        try {
            Method method = exception.getErrorItem().getClass().getMethod("getLabel");
            String messageFmt = (String) method.invoke(exception.getErrorItem());
            String message = String.format(messageFmt, exception.getArgs());
            return message;
        } catch (Exception e) {
            return "unknown error, failed to pick error message.";
        }
    }

    private void fillStack(BaseResponse resp,Throwable e){
        String errorStr = ExceptionUtils.getStackTrace(e);
        resp.setStack(errorStr);
    }
}