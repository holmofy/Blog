package cn.hff.blog.common;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

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
     * @see EnhancedJpaFactoryBean#createRepositoryFactory(EntityManager)
     * @see org.springframework.data.jpa.repository.support.JpaRepositoryFactory#getTargetRepository
     * @see org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration
     */
    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    public EnhancedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager,
                                 NamedParameterJdbcOperations namedParameterJdbcTemplate) {
        super(entityInformation, entityManager);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public EnhancedJpaRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
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
