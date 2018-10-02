package cn.hff.blog.service;

import java.util.List;

import javax.annotation.Nullable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.hff.blog.dao.ArticleDao.IdAndTitle;
import cn.hff.blog.dto.PageArticleDTO;
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

    Page<PageArticleDTO> getPage(@Nullable Boolean published, Pageable pageable);

    /**
     * 根据用户输入的标题前缀联想用户想要查询的文章
     *
     * @param titlePrefix 文章前缀
     * @param size        最多多少条
     * @return 相关的文章
     */
    List<IdAndTitle> likeTitlePrefix(String titlePrefix, int size);

}
