package cn.exsolo.auth.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author prestolive
 * @date 2021/9/3
 **/
public class SecurityUserContext {

    public static String getUserID(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = AccessUtil.getToken(request);
        if(token==null){
            return null;
        }
        return TokenUtil.getUserID(token);
    }
}
