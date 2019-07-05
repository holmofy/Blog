package cn.hff.blog;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import cn.hff.blog.config.DbConfig;

/**
 * @author holmofy
 */
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@RunWith(SpringRunner.class)
@ImportAutoConfiguration(DbConfig.class)
public abstract class BaseControllerTest<T> {
    /**
     * @see WebMvcTest
     * @see AutoConfigureMockMvc
     * @see org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration
     */
    @Autowired
    protected MockMvc mockMvc;

    /**
     * @see WebAppConfiguration
     */
    @Autowired
    protected MockHttpSession session;

    /**
     * @see AutoConfigureJsonTesters
     */
    @Autowired
    protected JacksonTester<T> json;
}
