package cn.hff.blog.common.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import cn.hff.blog.common.BaseJpaRepository;
import cn.hff.blog.common.entity.TestEntity;

public interface TestEntityDao extends BaseJpaRepository<TestEntity, Integer> {

    default List<TestEntity> findAll() {
        return getJdbcTemplate().query("select * from test_entity", new BeanPropertyRowMapper<>(TestEntity.class));
    }

}