package cn.hff.blog.common;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static cn.hff.blog.common.BaseJpaRepository.*;
import cn.hff.blog.common.dao.TestEntityDao;
import cn.hff.blog.common.entity.TestEntity;
import cn.hff.blog.common.entity.TestEntity_;
import static java.util.Arrays.asList;

/**
 * @author holmofy
 */
@DataJpaTest
@ImportAutoConfiguration(DbTestConfig.class)
@RunWith(SpringRunner.class)
public class EnhanceJpaRepositoryTest {

    @Autowired
    private TestEntityDao testEntityDao;

    @Before
    public void setUp() {
        testEntityDao.save(TestEntity.builder().stringField("1").integerField(2).longField(3L).build());
        testEntityDao.save(TestEntity.builder().stringField("001").integerField(22).longField(33L).build());
        testEntityDao.save(TestEntity.builder().stringField("003").integerField(222).longField(null).build());
        testEntityDao.save(TestEntity.builder().stringField("4").integerField(null).longField(null).build());
    }

    @Test
    public void test_alwaysTrue() {
        List<TestEntity> entities = testEntityDao.findAll(alwaysTrue());
        Assert.assertEquals(4, entities.size());
    }

    @Test
    public void test_alwaysFalse() {
        List<TestEntity> entities = testEntityDao.findAll(alwaysFalse());
        Assert.assertEquals(0, entities.size());
    }

    @Test
    public void test_isNull() {
        List<TestEntity> entities = testEntityDao.findAll(isNull(TestEntity_.integerField));
        Assert.assertEquals(1, entities.size());
    }

    @Test
    public void test_isNotNull() {
        List<TestEntity> entities = testEntityDao.findAll(isNotNull(TestEntity_.longField));
        Assert.assertEquals(2, entities.size());
    }

    @Test
    public void test_eq() {
        List<TestEntity> entities = testEntityDao.findAll(eq(TestEntity_.stringField, "1"));
        Assert.assertEquals(1, entities.size());
    }

    @Test
    public void test_lt() {
        List<TestEntity> entities = testEntityDao.findAll(lt(TestEntity_.integerField, 22));
        Assert.assertEquals(1, entities.size());
    }

    @Test
    public void test_lte() {
        List<TestEntity> entities = testEntityDao.findAll(lte(TestEntity_.integerField, 22));
        Assert.assertEquals(2, entities.size());
    }

    @Test
    public void test_gt() {
        List<TestEntity> entities = testEntityDao.findAll(gt(TestEntity_.integerField, 22));
        Assert.assertEquals(1, entities.size());
    }

    @Test
    public void test_gte() {
        List<TestEntity> entities = testEntityDao.findAll(gte(TestEntity_.integerField, 22));
        Assert.assertEquals(2, entities.size());
    }

    @Test
    public void test_between() {
        List<TestEntity> entities = testEntityDao.findAll(between(TestEntity_.integerField, 22, 222));
        Assert.assertEquals(2, entities.size());
    }

    @Test
    public void test_in() {
        List<TestEntity> entities = testEntityDao.findAll(in(TestEntity_.integerField, asList(22, 222)));
        Assert.assertEquals(2, entities.size());
    }

    @Test
    public void test_notIn() {
        // not in (22, 222) 不包括为null
        List<TestEntity> entities = testEntityDao.findAll(notIn(TestEntity_.integerField, asList(22, 222)));
        System.out.println(entities.size());
        Assert.assertEquals(1, entities.size());
    }

    @Test
    public void test_startsWith() {
        List<TestEntity> entities = testEntityDao.findAll(startsWith(TestEntity_.stringField, "00"));
        Assert.assertEquals(2, entities.size());
    }

}
