package cn.hff.blog;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.hff.blog.config.DbConfig;

/**
 * @author holmofy
 */
@DataJpaTest
@ImportAutoConfiguration(DbConfig.class)
@RunWith(SpringRunner.class)
public abstract class BaseDaoTest {
}
