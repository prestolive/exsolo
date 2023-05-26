package cn.exsolo.starter;

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

    @Bean(name="shiroFilterChain")
    public Map<String, String> shiroFilterChainDefinition(){
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/api/auth/**", "anon");
        filterChainDefinitionMap.put("/", "defaultAuthClient");
        filterChainDefinitionMap.put("/api/**", "anon");
        filterChainDefinitionMap.put("**", "defaultAuthClient");
        return  filterChainDefinitionMap;
    }
}
