package cn.hff.blog.common;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 自定义BaseRepository
 * https://docs.spring.io/spring-data/jpa/docs/2.0.3.RELEASE/reference/html/index.html#repositories.customize-base-repository
 *
 * @author Holmofy
 * @see org.springframework.data.repository.core.support.RepositoryFactorySupport
 * @see org.springframework.data.jpa.repository.support.JpaRepositoryFactory
 */
public class EnhancedJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements BaseJpaRepository<T, ID> {

    /**
     * @see org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EnhancedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public EnhancedJpaRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    /**
     * 这里没用构造器注入
     *
     * @see org.springframework.data.jpa.repository.support.JpaRepositoryFactory#getTargetRepository
     */
    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public NamedParameterJdbcOperations getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public JdbcOperations getJdbcTemplate() {
        return namedParameterJdbcTemplate.getJdbcOperations();
    }

}
