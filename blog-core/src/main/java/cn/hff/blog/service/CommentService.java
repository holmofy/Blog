package cn.hff.blog.service;

import cn.hff.blog.entity.Comment;

/**
 * 评论服务
 * <p>
 * Created by Holmofy on 2018/7/21.
 */
public interface CommentService {

    Comment addComment(Comment comment);

    void deleteComment(int commentId);

    Comment updateComment(Comment comment);

}
