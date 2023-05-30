package cn.exsolo.authserver.shiro;

import cn.exsolo.auth.shiro.LoginAuthenticationToken;
import cn.exsolo.authserver.service.AuthCacheService;
import cn.exsolo.authserver.utils.PasswordHelper;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author prestolive
 * @date 2023/5/26
 **/
@Component
public class LoginMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private AuthCacheService authCacheService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String loginCode = (String) token.getPrincipal();
        //重试校验
        Integer retryCount = authCacheService.addAuthRetryCount(loginCode);
        if (retryCount > 10) {
            throw new ExcessiveAttemptsException();
        }
        //将用户提交的密码，用用同等的方式编码
        LoginAuthenticationInfo loginInfo = (LoginAuthenticationInfo) info;
        LoginAuthenticationToken loginToken = (LoginAuthenticationToken) token;
        String salt = loginInfo.getSlat();
        String encryptStr = PasswordHelper.encryptPassword(loginToken.getPassword(), salt);
        loginToken.setCredentials(encryptStr);
        boolean check =  super.doCredentialsMatch(token, info);
        if(check){
            authCacheService.removeAuthRetryCount(loginCode);
            authCacheService.setCaptchaRequire(loginCode,false);
        }else{
            //超过3次要验证码
            if (retryCount> 3) {
                authCacheService.setCaptchaRequire(loginCode,true);
            }
        }
        return check;
    }
}
