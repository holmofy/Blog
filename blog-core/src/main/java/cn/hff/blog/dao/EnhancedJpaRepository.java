package cn.hff.blog.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.internal.SessionFactoryImpl;
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

    private EntityManager entityManager;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EnhancedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(entityManager));
    }

    private DataSource getDataSource(EntityManager entityManager) {
        // Hibernate SessionFactory
        SessionFactoryImpl sf = entityManager.getEntityManagerFactory().unwrap(SessionFactoryImpl.class);
        return ((DatasourceConnectionProviderImpl) sf.getServiceRegistry().getService(ConnectionProvider.class)).getDataSource();
    }

    @Override
    public NamedParameterJdbcOperations namedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    @Override
    public JdbcOperations jdbcTemplate() {
        return namedParameterJdbcTemplate.getJdbcOperations();
    }

    @Override
    public EntityManager entityManager() {
        return entityManager;
    }
}
