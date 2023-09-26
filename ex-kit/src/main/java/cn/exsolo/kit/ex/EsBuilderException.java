package cn.exsolo.kit.ex;

/**
 * @author prestolive
 * @date 2021/3/14
 **/
public class EsBuilderException extends RuntimeException{

    public EsBuilderException(String message) {
        super(message);
    }

    public EsBuilderException(String message, Throwable cause) {
        super(message, cause);
    }
}
