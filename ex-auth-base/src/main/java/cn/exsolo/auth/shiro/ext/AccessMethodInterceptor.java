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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author prestolive
 * @date 2023/7/11
 **/
public class AccessMethodInterceptor extends AuthorizingAnnotationMethodInterceptor implements MethodInterceptor {


    public AccessMethodInterceptor(AnnotationResolver resolver) {
        super(new AccessEmptyHandler(),resolver);
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
        subject.checkPermissions(keys);
    }

    private String[] getAnnotationKeys(String module, String node, MethodInvocation mi){
        List<String> keys = new ArrayList<>();
        AccessCommon ac = (AccessCommon) getResolver().getAnnotation(mi,AccessCommon.class);
        if(ac!=null){
            keys.add(getPermission(module,node,"common"));
        }
        AccessEdit ae = (AccessEdit) getResolver().getAnnotation(mi,AccessEdit.class);
        if(ae!=null){
            keys.add(getPermission(module,node,"edit"));
        }
        AccessView av = (AccessView) getResolver().getAnnotation(mi,AccessView.class);
        if(av!=null){
            keys.add(getPermission(module,node,"view"));
        }
        AccessConfig ag = (AccessConfig) getResolver().getAnnotation(mi,AccessConfig.class);
        if(ag!=null){
            keys.add(getPermission(module,node,"config"));
        }
        AccessCustom am = (AccessCustom) getResolver().getAnnotation(mi,AccessCustom.class);
        if(am!=null){
            keys.add(getPermission(module,node,am.key()));
        }
        return keys.toArray(new String[keys.size()]);
    }

    private String getPermission(String module,String node,String key){
        String permission = String.format("%s:%s:%s", module, node,key);
        return permission;
    }
}
