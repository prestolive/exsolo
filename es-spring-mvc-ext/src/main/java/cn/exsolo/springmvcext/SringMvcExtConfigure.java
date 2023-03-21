package cn.exsolo.springmvcext;

import cn.exsolo.springmvcext.in.RequestJSONArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author prestolive
 * @date 2023/1/18
 **/
@Configuration
public class SringMvcExtConfigure implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RequestJSONArgumentResolver());
    }

}
