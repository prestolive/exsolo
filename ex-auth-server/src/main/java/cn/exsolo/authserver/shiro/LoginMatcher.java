package cn.exsolo.authserver.shiro;

import cn.exsolo.auth.shiro.LoginAuthenticationToken;
import cn.exsolo.authserver.utils.PasswordHelper;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.shiro.cache.Cache;

/**
 * @author prestolive
 * @date 2023/5/26
 **/
public class LoginMatcher extends SimpleCredentialsMatcher {

    private Cache<String, AtomicInteger> passwordRetryCache;

    public LoginMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String loginCode = (String) token.getPrincipal();
        //重试校验
        AtomicInteger retryCount = passwordRetryCache.get(loginCode);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(loginCode, retryCount);
        }
        if (retryCount.incrementAndGet() > 5) {
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
            retryCount.set(0);
        }
        return check;
    }
}
