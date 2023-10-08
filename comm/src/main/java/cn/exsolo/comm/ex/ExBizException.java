package cn.exsolo.comm.ex;

/**
 * @author prestolive
 * @date 2021/5/26
 **/
public class ExBizException extends RuntimeException{

    public ExBizException() {
    }

    public ExBizException(String message) {
        super(message);
    }
}
