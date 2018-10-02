package cn.hff.blog.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import cn.hff.blog.mvc.CurrentUserHandlerMethodArgumentResolver;
import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * SpringMVC配置
 * https://docs.spring.io/spring-boot/docs/current/reference/html/howto-spring-mvc.html
 * <p>
 *
 * @author Holmofy
 * @see WebMvcAutoConfiguration
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer,
        Jackson2ObjectMapperBuilderCustomizer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 自定义@CurrentUser注解的参数解析器
        resolvers.add(new CurrentUserHandlerMethodArgumentResolver());
    }

    /**
     * @see JacksonAutoConfiguration
     * @see Jackson2ObjectMapperBuilder
     */
    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder.modules(customizeJackson2JavaTimeModule());
    }

    private SimpleModule customizeJackson2JavaTimeModule() {
        // 替换默认的ISO_LOCAL_DATE_TIME
        DateTimeFormatter standardDateTime = ofPattern("yyyy-MM-dd HH:mm:ss");
        return new JavaTimeModule()
                .addSerializer(new LocalDateTimeSerializer(standardDateTime))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(standardDateTime));
    }
}
