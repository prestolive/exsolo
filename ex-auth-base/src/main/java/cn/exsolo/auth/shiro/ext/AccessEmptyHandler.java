package cn.exsolo.auth.shiro.ext;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;

import java.lang.annotation.Annotation;

/**
 * @author prestolive
 * @date 2023/7/11
 **/
public class AccessEmptyHandler extends AuthorizingAnnotationHandler {

    public AccessEmptyHandler(Class<? extends Annotation> annotationClz) {
        //FIXME 这个有作用，只有注入了，才会触发过滤和realmdo的GetAuthorizationInfo的
        super(annotationClz);
    }

    @Override
    public void assertAuthorized(Annotation annotation) throws AuthorizationException {
        System.out.println(annotation);
        //do nothing
    }
}