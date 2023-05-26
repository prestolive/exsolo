package cn.exsolo.auth.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 账号密码登录用的shiro token
 * @author prestolive
 * @date 2023/5/25
 **/
public class LoginAuthenticationToken  implements AuthenticationToken {


    private String principal;

    private String password;

    private String credentials;

    public LoginAuthenticationToken(String loginCode, String password) {
        this.principal = loginCode;
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    public String getPassword() {
        return password;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }
}
