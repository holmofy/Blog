package cn.hff.blog.common;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

/**
 * @author holmofy
 */
public class EnhancedJpaFactoryBean<T extends Repository<S, ID>, S, ID> extends JpaRepositoryFactoryBean<T, S, ID>
        implements BeanFactoryAware {

    private BeanFactory beanFactory;

    /**
     * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public EnhancedJpaFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.beanFactory = beanFactory;
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        NamedParameterJdbcOperations namedParameterJdbcTemplate = beanFactory.getBean(NamedParameterJdbcOperations.class);
        return new EnhanceJpaFactory(entityManager, namedParameterJdbcTemplate);
    }

    static class EnhanceJpaFactory extends JpaRepositoryFactory {
        private NamedParameterJdbcOperations namedParameterJdbcTemplate;

        /**
         * Creates a new {@link JpaRepositoryFactory}.
         *
         * @param entityManager              must not be {@literal null}
         * @param namedParameterJdbcTemplate NamedParameterJdbcTemplate
         */
        public EnhanceJpaFactory(EntityManager entityManager, NamedParameterJdbcOperations namedParameterJdbcTemplate) {
            super(entityManager);
            this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        }

        @Override
        protected SimpleJpaRepository<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {

            JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());

            return getTargetRepositoryViaReflection(information, entityInformation, entityManager, namedParameterJdbcTemplate);
        }
    }

}
