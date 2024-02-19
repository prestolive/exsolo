package cn.exsolo.auth.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author prestolive
 * @date 2021/9/3
 **/
public class SecurityUserContext {

    public static String getUserID(){
        RequestAttributes attributes =  RequestContextHolder.getRequestAttributes();
        if(attributes!=null){
            HttpServletRequest request =((ServletRequestAttributes)  attributes).getRequest();
            String token = AccessUtil.getToken(request);
            if(token==null){
                return null;
            }
            return AccessTokenUtil.getUserID(token);
        }else{
            return null;
        }
    }
}
