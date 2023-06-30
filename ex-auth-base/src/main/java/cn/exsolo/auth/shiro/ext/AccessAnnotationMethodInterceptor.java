package cn.exsolo.auth.shiro.ext;

import org.apache.shiro.aop.AnnotationHandler;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

import java.lang.reflect.Method;

/**
 * @author prestolive
 * @date 2023/6/30
 **/
public class AccessAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {

    public AccessAnnotationMethodInterceptor() {
        super(new AccessHandler());
    }

    public AccessAnnotationMethodInterceptor(AnnotationResolver resolver) {
        super(new AccessHandler(), resolver);
    }

    @Override
    public void assertAuthorized(MethodInvocation mi) throws AuthorizationException {
        AnnotationHandler h = this.getHandler();
        if(h instanceof AccessHandler){
            AccessHandler handler = (AccessHandler) h;
            handler.assertAuthorized(getAnnotation(mi));
        }
    }

}
