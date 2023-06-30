package cn.exsolo.auth.shiro.ext;

import cn.exsolo.auth.shiro.ext.stereotype.Access;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author prestolive
 * @date 2023/6/30
 **/
public class AccessAuthorizationAttributeSourceAdvisor extends AuthorizationAttributeSourceAdvisor {

    public AccessAuthorizationAttributeSourceAdvisor() {
        setAdvice(new AccessAopAllianceAnnotationsAuthorizingMethodInterceptor());
    }

    @Override
    public boolean matches(Method method, Class targetClass) {
        return isAccessAnnotationPresent(method)  || super.matches(method, targetClass);
    }


    private boolean isAccessAnnotationPresent(Method method) {
        Annotation a = AnnotationUtils.findAnnotation(method, Access.class);
        if (a != null) {
            return true;
        }
        return false;
    }
}
