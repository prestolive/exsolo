package cn.exsolo.comm.ex;

/**
 * @author prestolive
 * @date 2023/3/28
 **/
public class ExDeclaredException extends ExBizException{

    private Enum errorItem;

    private Object[] args;

    private Object responseData;

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

    public ExDeclaredException withData(Object obj){
        this.responseData = obj;
        return this;
    }

    public Object getResponseData() {
        return responseData;
    }
}
