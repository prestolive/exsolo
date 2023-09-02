package cn.exsolo.auth.shiro.ext;

import cn.exsolo.auth.shiro.ext.stereotype.*;
import cn.exsolo.comm.ex.ExDevException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInterceptor;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author prestolive
 * @date 2023/7/11
 **/
public class AccessMethodInterceptor extends AuthorizingAnnotationMethodInterceptor implements MethodInterceptor {

    private Class<? extends Annotation> annotationClz;

    public AccessMethodInterceptor(Class<? extends Annotation> annotationClz,AnnotationResolver resolver) {
        super(new AccessEmptyHandler(annotationClz),resolver);
        this.annotationClz = annotationClz;
    }

    @Override
    public void assertAuthorized(MethodInvocation mi) throws AuthorizationException {
        assertAuth(mi);
    }

    private ConcurrentHashMap cache = new ConcurrentHashMap();

    private void assertAuth(MethodInvocation mi){
        Subject subject = SecurityUtils.getSubject();
        Class parent =  mi.getMethod().getDeclaringClass();
        AccessProvider accessProvider = (AccessProvider) parent.getAnnotation(AccessProvider.class);
        if(accessProvider==null){
            throw new ExDevException(" No annotation @AccessProvider set in target class "+parent.getName()+"");
        }
        String[] keys = getAnnotationKeys(accessProvider.module(),accessProvider.node(),mi);
        //FIXME 关闭权限校验
//        subject.checkPermissions(keys);
    }

    private String[] getAnnotationKeys(String module, String node, MethodInvocation mi){
        List<String> keys = new ArrayList<>();
        Annotation ac = getResolver().getAnnotation(mi,annotationClz);
        if(ac instanceof  AccessCommon){
            keys.add(getPermission(module,node,"common"));
        }else if(ac instanceof  AccessView){
            keys.add(getPermission(module,node,"view"));
        }else if(ac instanceof  AccessEdit){
            keys.add(getPermission(module,node,"edit"));
        }else if(ac instanceof  AccessConfig){
            keys.add(getPermission(module,node,"config"));
        }else if(ac instanceof  AccessCustom){
            AccessCustom accessCustom = (AccessCustom) ac;
            keys.add(getPermission(module,node,accessCustom.key()));
        }else{
            throw new RuntimeException("不支持的类型"+ac.getClass().getName());
        }
        return keys.toArray(new String[keys.size()]);
    }

    private String getPermission(String module,String node,String key){
        String permission = String.format("%s:%s:%s", module, node,key);
        return permission;
    }
}
