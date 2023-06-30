package cn.exsolo.auth;

import cn.exsolo.auth.shiro.DefaultAuthFilter;
import cn.exsolo.auth.shiro.DefaultRealm;
import cn.exsolo.auth.shiro.FixShiroAtLeastOneSuccessfulStrategy;
import cn.exsolo.auth.shiro.ext.AccessAuthorizationAttributeSourceAdvisor;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author prestolive
 * @date 2023/5/24
 **/
@Configuration
public class ShiroBaseConfiguration {

    /**
     * 关闭session
     * @return
     */
    @Bean
    public SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    /**
     * 持久化
     * @return
     */
    @Bean(name = "subjectDAO")
    public DefaultSubjectDAO subjectDAO() {
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        defaultSubjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        return defaultSubjectDAO;
    }

    /**
     * 核心
     * @param subjectDAO
     * @return
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager( @Qualifier("subjectDAO") DefaultSubjectDAO subjectDAO) {
        DefaultRealm defaultRealm = new DefaultRealm();
        //开启授权缓存
        defaultRealm.setAuthorizationCachingEnabled(true);
        defaultRealm.setCredentialsMatcher(new AllowAllCredentialsMatcher());
        //指定授权缓存的名字(与 ehcache.xml 中声明的相同)
        defaultRealm.setAuthorizationCacheName("shiro-authorizationCache");
        //
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(defaultRealm);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }


    @Bean("authenticator")
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new FixShiroAtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    /**
     * ehcache shiro的套接
     * 缓存管理器
     * 当有需要自定义缓存文件时
     * shiroCacheManager.setCacheManagerConfigFile("classpath:ehcache_shiro.xml");
     * @return
     */
    @Bean(name = "shiroCacheManager")
    public CacheManager shiroCacheManager(@Qualifier("cacheManager") net.sf.ehcache.CacheManager cacheManager) {
        EhCacheManager shiroCacheManager = new EhCacheManager();
        shiroCacheManager.setCacheManager(cacheManager);
        return shiroCacheManager;

    }

    /**
     * 注入自定义Filter
     *
     * @return
     */
    @Bean(name = "authFilter")
    public Map<String, Filter> LoginAuthcFilter() {
        Map<String, Filter> filters = new HashMap<>();
        filters.put("defaultAuthClient", new DefaultAuthFilter());
        return filters;
    }

    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setFilter(new DelegatingFilterProxy("shiroFilterFactoryBean"));
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager,
                                                         @Qualifier("authFilter") Map<String, Filter> filters,
                                                         @Qualifier("extFilter") Map<String, Filter> extFilter,
                                                         @Qualifier("shiroFilterChain") Map<String, String> shiroFilterChain) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);

        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setUnauthorizedUrl("/unauth");
        Map<String, Filter> allFilter = filters;
        if (extFilter != null) {
            allFilter.putAll(extFilter);
        }
        filterFactoryBean.setFilters(allFilter);
        // 配置过滤器
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChain);
        return filterFactoryBean;

    }

    @Bean
    @ConditionalOnMissingBean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AccessAuthorizationAttributeSourceAdvisor advisor = new AccessAuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
