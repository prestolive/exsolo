package cn.exsolo.kit.ex;

/**
 * @author prestolive
 * @date 2023/3/28
 **/
public class ExErrorCodeException extends RuntimeException{

    private Enum error_code;

    public ExErrorCodeException(Enum error_code,Object ...obj) {
        this.error_code = error_code;
    }

}
