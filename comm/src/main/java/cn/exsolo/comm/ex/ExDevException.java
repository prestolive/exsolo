package cn.exsolo.comm.ex;

/**
 * @author prestolive
 * @date 2021/5/26
 **/
public class ExDevException extends RuntimeException{

    public ExDevException() {
    }

    public ExDevException(String message) {
        super(message);
    }

    public ExDevException(String message,Throwable e) {
        super(message,e);
    }
}
