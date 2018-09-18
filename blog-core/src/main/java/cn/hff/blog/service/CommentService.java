package cn.hff.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hff.blog.entity.Comment;
import cn.hff.blog.entity.User;

/**
 * 评论服务
 * <p>
 * Created by Holmofy on 2018/7/21.
 */
public interface CommentService {

    Page<Comment> listComments(int articleId, Pageable pageable);

    Comment addComment(Comment comment);

    void deleteComment(User user, int commentId);

    Comment updateComment(User user, int commentId, Comment comment);

}
