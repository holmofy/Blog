package cn.hff.blog.service;

import java.util.List;

import javax.annotation.Nullable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hff.blog.dao.ArticleDao.IdAndTitle;
import cn.hff.blog.entity.Article;
import cn.hff.blog.entity.User;

/**
 * 文章
 * <p>
 * Created by Holmofy on 2018/6/18.
 */
public interface ArticleService {

    Article save(User user, Article article);

    Article get(int id);

    void delete(User user, int id);

    Article update(User user, int id, Article article);

    Page<Article> getPage(@Nullable Boolean published, Pageable pageable);

    /**
     * 根据用户输入的标题前缀联想用户想要查询的文章
     *
     * @param authorId    作者UserId
     * @param titlePrefix 文章前缀
     * @param pageable    分页参数
     * @return 相关的文章ID和标题
     */
    List<IdAndTitle> likeTitlePrefix(int authorId, String titlePrefix, Pageable pageable);

    /**
     * 根据标题模糊查询
     *
     * @param authorId   作者UserId
     * @param fuzzyTitle 模糊标题
     * @param pageable   分页参数
     * @return 相关文章
     */
    Page<Article> likeFuzzyTitle(int authorId, String fuzzyTitle, Pageable pageable);
}
