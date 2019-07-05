package cn.hff.blog.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.google.common.collect.Iterators;

import cn.hff.blog.BaseDaoTest;
import cn.hff.blog.dao.ArticleDao.IdAndTitle;
import cn.hff.blog.entity.Article;

/**
 * 数据访问层测试用例
 *
 * @author Holmofy
 */
public class ArticleDaoTest extends BaseDaoTest {

    @Autowired
    private ArticleDao articleDao;

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
