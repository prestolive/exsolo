package cn.exsolo.auth.shiro.ext;

import org.apache.shiro.spring.aop.SpringAnnotationResolver;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;

/**
 * @author prestolive
 * @date 2023/6/30
 **/
public class AccessAopMethodInterceptor extends AopAllianceAnnotationsAuthorizingMethodInterceptor {

    public AccessAopMethodInterceptor() {
        super();
        this.methodInterceptors.add(new AccessMethodInterceptor(new SpringAnnotationResolver()));
    }

}
