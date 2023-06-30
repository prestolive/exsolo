package cn.exsolo.auth.shiro.ext;

import cn.exsolo.auth.shiro.ext.stereotype.Access;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;

import java.lang.annotation.Annotation;

/**
 * @author prestolive
 * @date 2023/6/30
 **/
public class AccessHandler extends AuthorizingAnnotationHandler {

    public AccessHandler() {
        super(Access.class);
    }

    @Override
    public void assertAuthorized(Annotation anno) throws AuthorizationException {
        if(anno instanceof Access){
            Access access = (Access) anno;
            Class parent = anno.annotationType();
            System.out.println("####"+anno.getClass());
            System.out.println("####"+parent);
        }
    }
}
