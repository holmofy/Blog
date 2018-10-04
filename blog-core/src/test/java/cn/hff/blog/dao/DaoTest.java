package cn.hff.blog.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Iterators;

import cn.hff.blog.config.DBConfig;
import cn.hff.blog.dao.ArticleDao.IdAndTitle;
import cn.hff.blog.entity.Article;
import cn.hff.blog.entity.Category;
import cn.hff.blog.entity.Comment;

/**
 * 数据访问层测试用例，主要看IPV4地址是否保存成功
 *
 * @author Holmofy
 */
@DataJpaTest
@ImportAutoConfiguration(DBConfig.class)
@RunWith(SpringRunner.class)
public class DaoTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ArticleDao articleDao;

    @Test
    public void testCommentDao() {
        Comment comment = new Comment();
        comment.setIp("183.56.74.34");
        comment.setArticleId(10);// articleId必填字段
        Comment save = commentDao.save(comment);
        Assert.assertEquals(save.getIp(), "183.56.74.34");
        Assert.assertEquals(save.getArticleId().intValue(), 10);
    }

    @Test
    public void testCategoryDao() {
        Category category = new Category();
        category.setName("分类一");
        Category save = categoryDao.save(category);
        String name = categoryDao.getNameById(save.getId());
        Assert.assertEquals(name, "分类一");
    }

    @Test
    public void testArticleDao() {
        Article article = new Article();
        article.setAuthorId(10);
        article.setTitle("标题");
        Article save = articleDao.save(article);
        Assert.assertEquals(save.getTitle(), "标题");
        Integer authorId = articleDao.getAuthorIdById(save.getId());
        Assert.assertEquals(authorId.intValue(), 10);
    }

    @Test
    public void testLikeTitle() {
        Article article = new Article();
        article.setAuthorId(10);
        article.setTitle("标题ABC...");
        Article save = articleDao.save(article);
        Assert.assertNotNull(save);
        List<IdAndTitle> articles = articleDao.findByAuthorIdAndTitleStartsWith(10, "标题", PageRequest.of(0, 10));
        Assert.assertEquals(articles.size(), 1);
        Assert.assertEquals(Iterators.getLast(articles.iterator()).getTitle(), "标题ABC...");
    }
}
