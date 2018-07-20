package cn.hff.blog.config;

import java.util.Properties;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 数据库与事务相关配置
 * <p>
 * Created by Holmofy on 2018/6/22.
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "cn.hff.blog.dao")
public class DBConfig {

    /**
     * 声明拦截哪些方法,也就是通知(Advisor)
     * <p>
     * 参考Spring官方文档：https://docs.spring.io/spring/docs/5.0.7.RELEASE/spring-framework-reference/data-access.html#tx-decl-explained
     *
     * @return 方法拦截器
     */
    @Bean(name = "txAdvice")
    public TransactionInterceptor getAdvisor(PlatformTransactionManager txManager) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("get*", "PROPAGATION_REQUIRED,-Exception,readOnly");
        properties.setProperty("find*", "PROPAGATION_REQUIRED,-Exception,readOnly");
        properties.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("*", "PROPAGATION_REQUIRED,-Exception");
        return new TransactionInterceptor(txManager, properties);
    }

    /**
     * 指定对那些类进行AOP代理
     */
    @Bean
    public BeanNameAutoProxyCreator txProxy() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setInterceptorNames("txAdvice");
        creator.setBeanNames("*Service", "*ServiceImpl");
        creator.setProxyTargetClass(true);
        return creator;
    }
}
