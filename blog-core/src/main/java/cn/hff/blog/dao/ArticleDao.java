package cn.hff.blog.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import cn.hff.blog.common.BaseJpaRepository;
import cn.hff.blog.entity.Article;

/**
 * {@link Article}的数据访问对象
 *
 * @author Holmofy
 */
public interface ArticleDao extends BaseJpaRepository<Article, Integer> {

    @Query("select authorId from Article where id=?1")
    Integer getAuthorIdById(int id);

    Page<Article> findByPublished(boolean published, Pageable pageable);

    List<IdAndTitle> findByAuthorIdAndTitleStartsWith(int authorId, String titlePrefix, Pageable pageable);

    interface IdAndTitle {
        Integer getId();

        String getTitle();
    }

    Page<Article> findByAuthorIdAndTitleLike(int authorId, String fuzzyTitle, Pageable pageable);
}
