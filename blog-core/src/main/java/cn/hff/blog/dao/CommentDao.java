package cn.hff.blog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hff.blog.common.BaseJpaRepository;
import cn.hff.blog.entity.Comment;

/**
 * {@link Comment}的数据访问对象
 *
 * @author Holmofy
 */
public interface CommentDao extends BaseJpaRepository<Comment, Integer> {

    Integer countByArticleId(Integer articleId);

    Page<Comment> findByArticleId(int articleId, Pageable pageable);

}
