package cn.exsolo.auth.shiro.ext;

import cn.exsolo.auth.shiro.ext.stereotype.*;
import org.apache.shiro.spring.aop.SpringAnnotationResolver;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;

/**
 * @author prestolive
 * @date 2021/6/30
 **/
public class AccessAopMethodInterceptor extends AopAllianceAnnotationsAuthorizingMethodInterceptor {

    public AccessAopMethodInterceptor() {
        super();
        SpringAnnotationResolver resolver = new SpringAnnotationResolver();
        this.methodInterceptors.add(new AccessMethodInterceptor(AccessCommon.class,resolver));
        this.methodInterceptors.add(new AccessMethodInterceptor(AccessView.class,resolver));
        this.methodInterceptors.add(new AccessMethodInterceptor(AccessEdit.class,resolver));
        this.methodInterceptors.add(new AccessMethodInterceptor(AccessConfig.class,resolver));
        this.methodInterceptors.add(new AccessMethodInterceptor(AccessCustom.class,resolver));
    }

}
