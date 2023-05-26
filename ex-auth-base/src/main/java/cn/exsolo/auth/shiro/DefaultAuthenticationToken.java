package cn.exsolo.auth.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author prestolive
 * @date 2023/5/25
 **/
public class DefaultAuthenticationToken implements AuthenticationToken {

    private String token;

    public DefaultAuthenticationToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getToken() {
        return token;
    }
}
