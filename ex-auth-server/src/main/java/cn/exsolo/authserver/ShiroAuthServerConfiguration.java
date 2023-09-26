package cn.exsolo.authserver;

import cn.exsolo.authserver.shiro.LoginMatcher;
import cn.exsolo.authserver.shiro.LoginRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author prestolive
 * @date 2021/5/24
 **/
@Configuration
public class ShiroAuthServerConfiguration {

    @Bean("defaultAuthServerRealm")
    public Realm getDefaultAuthServerRealm( LoginMatcher loginMatcher) {
        LoginRealm loginRealm = new LoginRealm();
        loginRealm.setCredentialsMatcher(loginMatcher);
        //开启授权缓存
        loginRealm.setAuthorizationCachingEnabled(true);
        //指定授权缓存的名字(与 ehcache.xml 中声明的相同)
        loginRealm.setAuthorizationCacheName("shiro-authorizationCache");
        return loginRealm;
    }


}
