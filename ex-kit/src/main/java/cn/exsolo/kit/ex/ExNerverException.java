package cn.exsolo.kit.ex;

/**
 * You'd better not throws
 * @author prestolive
 * @date 2023/4/1
 **/
public class ExNerverException extends ExErrorCodeException{

    public ExNerverException(Enum error_code) {
        super(error_code);
    }
}
