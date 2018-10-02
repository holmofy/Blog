package cn.hff.blog.dao;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * 扩展JPA2标准中的<a href="https://en.m.wikibooks.org/wiki/Java_Persistence/Criteria">Criteria API</a>
 *
 * @param <T>  Entity Type
 * @param <ID> ID Type
 * @author Holmofy
 * @see Specification
 * @see Specifications
 * @see CriteriaBuilder
 * @see org.hibernate.Criteria
 * @see org.hibernate.criterion.Restrictions
 * @see org.hibernate.jpa.criteria.CriteriaBuilderImpl
 */
@NoRepositoryBean
public interface BaseJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    NamedParameterJdbcOperations namedParameterJdbcTemplate();

    JdbcOperations jdbcTemplate();

    static <T, V extends Comparable<? super V>> Specification<T> isNull(SingularAttribute<T, V> attr) {
        return (root, query, cb) -> cb.isNull(root.get(attr));
    }

    static <T, V extends Comparable<? super V>> Specification<T> isNotNull(SingularAttribute<T, V> attr) {
        return (root, query, cb) -> cb.isNotNull(root.get(attr));
    }

    static <T> Specification<T> eq(SingularAttribute<T, ?> attr, Object value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.equal(root.get(attr), value);
    }

    static <T> Specification<T> notEq(SingularAttribute<T, ?> attr, Object value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.notEqual(root.get(attr), value);
    }

    static <T, V extends Comparable<? super V>> Specification<T> lt(SingularAttribute<T, V> attr, V value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.lessThan(root.get(attr), value);
    }

    static <T, V extends Comparable<? super V>> Specification<T> lte(SingularAttribute<T, V> attr, V value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attr), value);
    }

    static <T, V extends Comparable<? super V>> Specification<T> gt(SingularAttribute<T, V> attr, V value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.greaterThan(root.get(attr), value);
    }

    static <T, V extends Comparable<? super V>> Specification<T> gte(SingularAttribute<T, V> attr, V value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attr), value);
    }

    static <T, V extends Comparable<? super V>> Specification<T> between(SingularAttribute<T, V> attr, V from, V to) {
        if (from == null) return lte(attr, to);
        if (to == null) return gte(attr, from);
        return (root, query, cb) -> cb.between(root.get(attr), from, to);
    }

    static <T> Specification<T> alwaysTrue() {
        return (root, query, cb) -> cb.isTrue(cb.literal(TRUE));
    }

    static <T> Specification<T> alwaysFalse() {
        return (root, query, cb) -> cb.isTrue(cb.literal(FALSE));
    }

    static <T> Specification<T> in(SingularAttribute<T, ?> attr, Collection<?> values) {
        if (values == null) return null;
        if (values.isEmpty()) return alwaysFalse();
        return (root, query, cb) -> cb.isTrue(root.get(attr).in(values));
    }

    static <T> Specification<T> notIn(SingularAttribute<T, ?> attr, Collection<?> values) {
        if (values == null) return null;
        if (values.isEmpty()) return alwaysFalse();
        return (root, query, cb) -> cb.isTrue(root.get(attr).in(values).not());
    }

    static <T> Specification<T> startsWith(SingularAttribute<T, ?> attr, Object value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.like(root.get(attr.getName()), value.toString() + "%");
    }
}
