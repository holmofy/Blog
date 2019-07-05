package cn.hff.blog.common;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import cn.hff.blog.common.dao.TestEntityDao;
import cn.hff.blog.common.entity.TestEntity;

/**
 * @author holmofy
 */
@DataJpaTest
@ImportAutoConfiguration(DbTestConfig.class)
@RunWith(SpringRunner.class)
public class JdbcTemplateTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    TestEntityDao testEntityDao;

    @Before
    public void setUp() {
        jdbcTemplate.execute("insert into test_entity(string_field, integer_field, long_field)\n" +
                "values('string',1,111)");
        Assert.assertEquals(1, countRowsInTable("test_entity"));
    }

    @Test
    public void test_query() {
        List<TestEntity> result = testEntityDao.findAll();
        Assert.assertEquals(1, result.size());
    }

}
