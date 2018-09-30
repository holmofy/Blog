package cn.hff.blog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hff.blog.entity.Comment;

/**
 * {@link Comment}的数据访问对象
 * <p>
 * Created by Holmofy on 2018/6/18.
 */
public interface CommentDao extends BaseJpaRepository<Comment, Integer> {

    Integer countByArticleId(Integer articleId);

    Page<Comment> findAllByArticleId(int articleId, Pageable pageable);
}
