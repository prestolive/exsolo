package cn.exsolo.starter;

import cn.exsolo.kit.cache.IExCache;
import cn.exsolo.kit.cache.impl.ExCacheEhCacheImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author prestolive
 * @date 2023/5/25
 **/
@Configuration
public class ExSoloConfiguration {

    /**
     * ehcache原版
     * @return
     */
    @Bean(name = "cacheManager")
    public net.sf.ehcache.CacheManager cacheManager(){
        net.sf.ehcache.CacheManager cacheManager=  new net.sf.ehcache.CacheManager();
        return cacheManager;
    }

    @Bean(name = "iExCache")
    public IExCache iExCache(net.sf.ehcache.CacheManager cacheManager){
        return new ExCacheEhCacheImpl(cacheManager);
    }


    @Bean(name="shiroFilterChain")
    public Map<String, String> shiroFilterChainDefinition(){
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/api/auth/**", "anon");
        filterChainDefinitionMap.put("/", "defaultAuthClient");
        //FIXME
        filterChainDefinitionMap.put("/api/**", "defaultAuthClient");
//        filterChainDefinitionMap.put("/api/**", "defaultAuthClient");
        filterChainDefinitionMap.put("**", "anon");
        return  filterChainDefinitionMap;
    }
}
