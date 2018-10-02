package cn.hff.blog.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import cn.hff.blog.dto.PageArticleDTO;
import cn.hff.blog.entity.Article;

/**
 * {@link Article}的数据访问对象
 * <p>
 * Created by Holmofy on 2018/6/18.
 */
public interface ArticleDao extends BaseJpaRepository<Article, Integer> {

    @Query("select authorId from Article where id=?1")
    Integer getAuthorIdById(int id);

    @Query("select new cn.hff.blog.dto.PageArticleDTO(" +
            "   a.id,a.title,a.createTime,a.updateTime,a.views,a.comments,a.categoryId,a.authorId,c.name) " +
            "from Article a join Category c " +
            "on a.categoryId=c.id")
    Page<PageArticleDTO> findAllByPage(Pageable pageable);

    @Query("select new cn.hff.blog.dto.PageArticleDTO(" +
            "   a.id,a.title,a.createTime,a.updateTime,a.views,a.comments,a.categoryId,a.authorId,c.name) " +
            "from Article a join Category c " +
            "on a.categoryId=c.id and a.published=?1")
    Page<PageArticleDTO> findAllByPage(boolean published, Pageable pageable);

    @Query(value = "SELECT id, title FROM tb_article WHERE title LIKE ?1% LIMIT ?2", nativeQuery = true)
    List<IdAndTitle> findByTitleStartingWith(String titlePrefix, int size);

    interface IdAndTitle {
        Integer getId();

        String getTitle();
    }
}
