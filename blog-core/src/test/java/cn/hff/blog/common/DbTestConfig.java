package cn.hff.blog.common;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import cn.hff.blog.common.dao.TestEntityDao;
import cn.hff.blog.common.entity.TestEntity;

@Configuration
@EnableJpaAuditing
@AutoConfigureAfter(JdbcTemplateAutoConfiguration.class)
@EntityScan(basePackageClasses = TestEntity.class)
@EnableJpaRepositories(
        basePackageClasses = TestEntityDao.class,
        repositoryBaseClass = EnhancedJpaRepository.class,
        repositoryFactoryBeanClass = EnhancedJpaFactoryBean.class
)
public class DbTestConfig {
}