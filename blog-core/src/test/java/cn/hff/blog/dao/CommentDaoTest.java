package cn.hff.blog.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hff.blog.BaseDaoTest;
import cn.hff.blog.entity.Comment;

/**
 * @author holmofy
 */
public class CommentDaoTest extends BaseDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Test
    public void testCommentDao() {
        Comment comment = new Comment();
        comment.setIp("183.56.74.34");
        comment.setArticleId(10);// articleId必填字段
        Comment save = commentDao.save(comment);
        Assert.assertEquals(save.getIp(), "183.56.74.34");
        Assert.assertEquals(save.getArticleId().intValue(), 10);
    }
}
