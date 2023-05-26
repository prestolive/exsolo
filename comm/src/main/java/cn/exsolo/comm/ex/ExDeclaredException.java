package cn.exsolo.comm.ex;

/**
 * @author prestolive
 * @date 2023/3/28
 **/
public class ExDeclaredException extends RuntimeException{

    private Enum errorItem;

    private Object[] args;

    public ExDeclaredException(Enum errorItem, Object ...args) {
        this.errorItem = errorItem;
        this.args = args;
    }

    public Enum getErrorItem() {
        return errorItem;
    }

    public Object[] getArgs() {
        return args;
    }
}
