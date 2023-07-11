package cn.exsolo.auth.shiro.ext;

import cn.exsolo.auth.shiro.ext.stereotype.AccessCommon;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;

import java.lang.annotation.Annotation;

/**
 * @author prestolive
 * @date 2023/7/11
 **/
public class AccessEmptyHandler extends AuthorizingAnnotationHandler {

    public AccessEmptyHandler() {
        //这个没有任何作者用，确保不报错而已
        super(AccessCommon.class);
    }

    @Override
    public void assertAuthorized(Annotation annotation) throws AuthorizationException {
        //do nothing
    }
}