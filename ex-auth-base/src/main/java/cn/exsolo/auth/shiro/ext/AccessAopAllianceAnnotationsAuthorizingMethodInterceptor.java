package cn.exsolo.auth.shiro.ext;

import org.apache.shiro.spring.aop.SpringAnnotationResolver;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;

/**
 * @author prestolive
 * @date 2023/6/30
 **/
public class AccessAopAllianceAnnotationsAuthorizingMethodInterceptor extends AopAllianceAnnotationsAuthorizingMethodInterceptor {

    public AccessAopAllianceAnnotationsAuthorizingMethodInterceptor() {
        super();
        this.methodInterceptors.add(new AccessAnnotationMethodInterceptor(new SpringAnnotationResolver()));
    }

}
