package cn.hff.blog.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import cn.hff.blog.common.EnhancedJpaRepository;

/**
 * 数据库与事务相关配置
 * <p>
 * Created by Holmofy on 2018/6/22.
 */
@Configuration
@EnableJpaAuditing
@AutoConfigureAfter(JdbcTemplateAutoConfiguration.class)
@EnableJpaRepositories(
        basePackages = "cn.hff.blog.dao",
        repositoryBaseClass = EnhancedJpaRepository.class
)
public class DBConfig {

}
