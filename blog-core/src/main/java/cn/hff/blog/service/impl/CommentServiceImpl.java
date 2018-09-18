package cn.hff.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.hff.blog.dao.CommentDao;
import cn.hff.blog.entity.Comment;
import cn.hff.blog.entity.User;
import cn.hff.blog.exception.NotFoundException;
import cn.hff.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public Page<Comment> listComments(int articleId, Pageable pageable) {
        return commentDao.findAllByArticleId(articleId, pageable);
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentDao.save(comment);
    }

    @Override
    public void deleteComment(User user, int commentId) {

    }

    @Override
    public Comment updateComment(User user, int id, Comment comment) {
        return null;
    }
}
