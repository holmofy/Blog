package cn.hff.blog.controller;

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

    @Test
    public void testGet() throws Exception {
        Article article = new Article();
        article.setTitle("标题");
        article.setAuthorId(10);
        article.setContent("文章内容");
        Article save = articleDao.save(article);
        Integer id = save.getId();
        mockMvc.perform(get("/api/article/" + id)
                .accept(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("标题"))
                .andExpect(jsonPath("$.authorId").value(10))
                .andExpect(jsonPath("$.content").value("文章内容"));
    }

    private User registerAndLogin() throws Exception {
        User user = userService.register(User.builder().userName("holmofy").password("123456").build());

        mockMvc.perform(post("/api/user/login")
                .session(session) // 登陆信息保存会话
                .param("principal", "holmofy")
                .param("credential", "123456"))
                .andExpect(jsonPath("$.userName").value("holmofy"));

        return user;
    }

    /**
     * 发文章必须会验证当前用户，所以必须要在同一会话中
     *
     * @throws Exception
     */
    @Test
    @DirtiesContext
    public void testPost() throws Exception {

        User user = registerAndLogin();

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
                .andExpect(jsonPath("$.authorId").value(user.getId()))
                .andExpect(jsonPath("$.content").value(article.getContent()));
    }

    @Test
    @DirtiesContext
    public void testDelete() throws Exception {

        User user = registerAndLogin();

        Article myArticle = new Article();
        myArticle.setTitle("标题");
        myArticle.setAuthorId(user.getId());
        myArticle.setContent("文章内容");
        myArticle = articleDao.save(myArticle);

        mockMvc.perform(delete("/api/article/" + myArticle.getId())
                .session(session))
                .andExpect(status().isOk());

        Article otherArticle = new Article();
        otherArticle.setTitle("标题");
        otherArticle.setAuthorId(user.getId() + 1);
        otherArticle.setContent("文章内容");
        otherArticle = articleDao.save(otherArticle);

        mockMvc.perform(delete("/api/article/" + otherArticle.getId())
                .session(session))
                .andExpect(status().isForbidden());
    }
}
