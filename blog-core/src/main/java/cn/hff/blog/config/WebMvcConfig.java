package cn.hff.blog.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.hff.blog.mvc.CurrentUserHandlerMethodArgumentResolver;

/**
 * SpringMVC配置
 * <p>
 * Created by Holmofy on 2018/6/21.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 自定义@CurrentUser注解的参数解析器
        resolvers.add(new CurrentUserHandlerMethodArgumentResolver());
        // SpringData分页组件参数解析器
        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();
        pageableResolver.setOneIndexedParameters(true); // 页码从1开始
        resolvers.add(pageableResolver);
    }
}
