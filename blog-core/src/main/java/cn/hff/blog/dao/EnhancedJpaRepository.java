package cn.hff.blog.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 自定义Repository
 * https://docs.spring.io/spring-data/jpa/docs/2.0.3.RELEASE/reference/html/index.html#repositories.customize-base-repository
 *
 * @author Holmofy
 */
public class EnhancedJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements BaseJpaRepository<T, ID> {

    /**
     * @see org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EnhancedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager,
                                 NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(entityInformation, entityManager);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public NamedParameterJdbcOperations namedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public JdbcOperations jdbcTemplate() {
        return namedParameterJdbcTemplate.getJdbcOperations();
    }

}
