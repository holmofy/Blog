package cn.hff.blog.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import cn.hff.blog.common.BaseJpaRepository;
import static cn.hff.blog.common.BaseJpaRepository.eq;
import cn.hff.blog.dto.ArticleSearchDto;
import cn.hff.blog.entity.Article;
import cn.hff.blog.entity.Article_;

/**
 * {@link Article}的数据访问对象
 *
 * @author Holmofy
 */
public interface ArticleDao extends BaseJpaRepository<Article, Integer> {

    @Query("select authorId from Article where id=?1")
    Integer getAuthorIdById(int id);

    @Query("update Article set deleted=true where id=?1")
    void safeDeleteById(int id);

    default Page<Article> findBySearch(ArticleSearchDto search, Pageable pageable) {
        Specification.where(eq(Article_.published, search.getPublished()))
                .and(eq(Article_.deleted, search.getDeleted()));
        return findAll(pageable);
    }

    List<IdAndTitle> findByAuthorIdAndTitleStartsWith(int authorId, String titlePrefix, Pageable pageable);

    interface IdAndTitle {
        Integer getId();

        String getTitle();
    }

    Page<Article> findByAuthorIdAndTitleLike(int authorId, String fuzzyTitle, Pageable pageable);
}
