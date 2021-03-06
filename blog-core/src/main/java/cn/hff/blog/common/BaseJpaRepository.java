package cn.hff.blog.common;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Nullable;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.Transactional;

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
 * @see org.springframework.data.jpa.domain.Specifications
 * @see javax.persistence.criteria.CriteriaBuilder
 * @see org.hibernate.Criteria
 * @see org.hibernate.criterion.Restrictions
 * @see org.hibernate.jpa.criteria.CriteriaBuilderImpl
 * @see EnhancedJpaRepository
 */
@Transactional
@NoRepositoryBean
public interface BaseJpaRepository<T, ID extends Serializable>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    NamedParameterJdbcOperations getNamedParameterJdbcTemplate();

    JdbcOperations getJdbcTemplate();

    static <T> Specification<T> alwaysTrue() {
        return (root, query, cb) -> cb.isTrue(cb.literal(TRUE));
    }

    static <T> Specification<T> alwaysFalse() {
        return (root, query, cb) -> cb.isTrue(cb.literal(FALSE));
    }

    static <T, V extends Comparable<? super V>> Specification<T> isNull(SingularAttribute<T, V> attr) {
        return (root, query, cb) -> cb.isNull(root.get(attr));
    }

    static <T, V extends Comparable<? super V>> Specification<T> isNotNull(SingularAttribute<T, V> attr) {
        return (root, query, cb) -> cb.isNotNull(root.get(attr));
    }

    @Nullable
    static <T> Specification<T> eq(SingularAttribute<T, ?> attr, @Nullable Object value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.equal(root.get(attr), value);
    }

    @Nullable
    static <T> Specification<T> notEq(SingularAttribute<T, ?> attr, @Nullable Object value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.notEqual(root.get(attr), value);
    }

    @Nullable
    static <T, V extends Comparable<? super V>> Specification<T> lt(SingularAttribute<T, V> attr, @Nullable V value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.lessThan(root.get(attr), value);
    }

    @Nullable
    static <T, V extends Comparable<? super V>> Specification<T> lte(SingularAttribute<T, V> attr, @Nullable V value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attr), value);
    }

    @Nullable
    static <T, V extends Comparable<? super V>> Specification<T> gt(SingularAttribute<T, V> attr, @Nullable V value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.greaterThan(root.get(attr), value);
    }

    @Nullable
    static <T, V extends Comparable<? super V>> Specification<T> gte(SingularAttribute<T, V> attr, @Nullable V value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attr), value);
    }

    @Nullable
    static <T, V extends Comparable<? super V>> Specification<T> between(SingularAttribute<T, V> attr, @Nullable V from, @Nullable V to) {
        if (from == null) return lte(attr, to);
        if (to == null) return gte(attr, from);
        return (root, query, cb) -> cb.between(root.get(attr), from, to);
    }

    @Nullable
    static <T> Specification<T> in(SingularAttribute<T, ?> attr, @Nullable Collection<?> values) {
        if (values == null) return null;
        if (values.isEmpty()) return alwaysFalse();
        return (root, query, cb) -> cb.isTrue(root.get(attr).in(values));
    }

    @Nullable
    static <T> Specification<T> notIn(SingularAttribute<T, ?> attr, @Nullable Collection<?> values) {
        if (values == null) return null;
        if (values.isEmpty()) return alwaysFalse();
        return (root, query, cb) -> cb.isTrue(root.get(attr).in(values).not());
    }

    @Nullable
    static <T> Specification<T> startsWith(SingularAttribute<T, ?> attr, @Nullable CharSequence value) {
        return isEmpty(value) ? null : (root, query, cb) -> cb.like(root.get(attr.getName()), value.toString() + "%");
    }
}
