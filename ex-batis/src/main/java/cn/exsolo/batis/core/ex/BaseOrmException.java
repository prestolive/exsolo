package cn.exsolo.batis.core.ex;

/**
 * Created by prestolive on 2017/6/15.
 */
public class BaseOrmException extends RuntimeException {

    public BaseOrmException(String reason) {
        super(reason);
    }

    public BaseOrmException(String reason, Throwable cause) {
        super(reason,cause);
    }
}
