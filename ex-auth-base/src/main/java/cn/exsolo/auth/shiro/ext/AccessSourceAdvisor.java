package cn.exsolo.auth.shiro.ext;

import cn.exsolo.auth.shiro.ext.stereotype.*;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * @author prestolive
 * @date 2021/6/30
 **/
public class AccessSourceAdvisor extends AuthorizationAttributeSourceAdvisor {

    public AccessSourceAdvisor() {
        setAdvice(new AccessAopMethodInterceptor());
    }

    @Override
    public boolean matches(Method method, Class targetClass) {
        return super.matches(method, targetClass) || isAccessAnnotationPresent(method);
    }


    private boolean isAccessAnnotationPresent(Method method) {
        if (AnnotationUtils.findAnnotation(method, AccessCommon.class)!=null) {
            return true;
        }
        if (AnnotationUtils.findAnnotation(method, AccessEdit.class)!=null) {
            return true;
        }
        if (AnnotationUtils.findAnnotation(method, AccessView.class)!=null) {
            return true;
        }
        if (AnnotationUtils.findAnnotation(method, AccessConfig.class)!=null) {
            return true;
        }
        if (AnnotationUtils.findAnnotation(method, AccessCustom.class)!=null) {
            return true;
        }
        return false;
    }
}
