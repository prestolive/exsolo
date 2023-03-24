package cn.exsolo.springmvcext.in.out;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<?> handleGlobalException(Exception e) {
        BaseResponse<?> baseResponse = handleBaseException(e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        baseResponse.setCode(status.value());
        baseResponse.setMessage(status.getReasonPhrase());
        return baseResponse;
    }

    private <T> BaseResponse<T> handleBaseException(Throwable t) {
        Assert.notNull(t, "Throwable must not be null");
        t.printStackTrace();
        BaseResponse<T> baseResponse = new BaseResponse<>(-1,t.getMessage());
        baseResponse.setMessage(t.getMessage());
        return baseResponse;
    }
}