package cn.hff.blog.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import cn.hff.blog.common.Constants;
import cn.hff.blog.entity.User;
import cn.hff.blog.service.UserService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller测试用例
 * <p>
 * Created by Holmofy on 2018/6/20.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    JacksonTester<User> json;

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void setup() {
        user = User.builder().username("holmofy").password("123456").build();
    }

    /**
     * 这里保存holmofy用户到内存数据库中会影响其他单元测试，
     * 所以@DirtiesContext让它重新加载上下文
     */
    @Test
    @DirtiesContext
    public void testRegister() throws Exception {
        mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json.write(user).getJson()))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("holmofy"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @DirtiesContext
    public void testLogin() throws Exception {
        User register = userService.register(user);
        mockMvc.perform(post("/api/session")
                .param("principal", "holmofy")
                .param("credential", "123456"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(request().sessionAttribute(Constants.LOGIN_USER_SESSION_KEY, register))
                .andExpect(jsonPath("$.username").value("holmofy"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }
}
