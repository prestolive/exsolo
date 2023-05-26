package cn.exsolo.authserver.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 * @author prestolive
 * @date 2023/5/27
 **/
public class LoginAuthenticationInfo implements AuthenticationInfo {

    protected PrincipalCollection principals;

    protected Object credentials;

    private String slat;

    public LoginAuthenticationInfo(Object principals, Object credentials, String slat) {
        this.principals = new SimplePrincipalCollection(principals, principals.toString());
        this.credentials = credentials;
        this.slat = slat;
    }

    @Override
    public PrincipalCollection getPrincipals() {
        return principals;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    public String getSlat() {
        return slat;
    }
}
