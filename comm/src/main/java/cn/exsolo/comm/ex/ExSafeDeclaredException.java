package cn.exsolo.comm.ex;

/**
 * @author prestolive
 * @date 2021/3/28
 **/
public class ExSafeDeclaredException extends ExDeclaredException{

    public ExSafeDeclaredException(Enum errorItem, Object... args) {
        super(errorItem, args);
    }
}
