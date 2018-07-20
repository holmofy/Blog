package cn.hff.blog.dao;

import cn.hff.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link Comment}的数据访问对象
 * <p>
 * Created by Holmofy on 2018/6/18.
 */
public interface CommentDao extends JpaRepository<Comment, Integer> {

    Integer countByArticleId(Integer articleId);

}
