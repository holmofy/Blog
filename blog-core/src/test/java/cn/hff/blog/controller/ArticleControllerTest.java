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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import cn.hff.blog.dao.ArticleDao;
import cn.hff.blog.entity.Article;
import cn.hff.blog.entity.User;
import cn.hff.blog.service.UserService;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Holmofy on 2018/6/23.
 */
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@RunWith(SpringRunner.class)
public class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MockHttpSession session;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    JacksonTester<Article> json;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private UserService userService;

    private User ownerUser;
    private Article myArticle;
    private Article otherArticle;

    @Before
    public void setup() {
        ownerUser = User.builder().username("holmofy").password("123456").build();
        ownerUser = userService.register(ownerUser);

        myArticle = new Article();
        myArticle.setTitle("标题");
        myArticle.setAuthorId(ownerUser.getId());
        myArticle.setContent("文章内容");
        myArticle = articleDao.save(myArticle);

        otherArticle = new Article();
        otherArticle.setTitle("别人的文章");
        otherArticle.setAuthorId(ownerUser.getId() + 1);
        otherArticle.setContent("文章内容...");
        otherArticle = articleDao.save(otherArticle);
    }

    private void login() throws Exception {
        mockMvc.perform(post("/api/session")
                .session(session) // 登陆信息保存会话
                .param("principal", "holmofy")
                .param("credential", "123456"))
                .andExpect(jsonPath("$.username").value("holmofy"));
    }

    @Test
    @DirtiesContext
    public void testGet() throws Exception {
        mockMvc.perform(get("/api/article/" + myArticle.getId())
                .accept(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(myArticle.getId()))
                .andExpect(jsonPath("$.title").value("标题"))
                .andExpect(jsonPath("$.authorId").value(ownerUser.getId()))
                .andExpect(jsonPath("$.content").value("文章内容"));
    }

    /**
     * 发文章必须会验证当前用户，所以必须要在同一会话中
     */
    @Test
    @DirtiesContext
    public void testPost() throws Exception {
        login();

        Article article = new Article();
        article.setTitle("标题");
        article.setContent("文章内容");

        mockMvc.perform(post("/api/article")
                .session(session)
                .contentType(APPLICATION_JSON_UTF8)
                .content(json.write(article).getJson()))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value(article.getTitle()))
                .andExpect(jsonPath("$.authorId").value(ownerUser.getId()))
                .andExpect(jsonPath("$.content").value(article.getContent()));
    }

    @Test
    @DirtiesContext
    public void testDelete() throws Exception {
        login();

        mockMvc.perform(delete("/api/article/" + myArticle.getId())
                .session(session))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/article/" + otherArticle.getId())
                .session(session))
                .andExpect(status().isForbidden());
    }
}
