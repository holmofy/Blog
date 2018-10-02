package cn.hff.blog.service.impl;

import java.util.List;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import cn.hff.blog.dao.ArticleDao;
import cn.hff.blog.dao.ArticleDao.IdAndTitle;
import cn.hff.blog.dto.PageArticleDTO;
import cn.hff.blog.entity.Article;
import cn.hff.blog.entity.User;
import cn.hff.blog.exception.NotFoundException;
import cn.hff.blog.exception.PermissionDeniedException;
import cn.hff.blog.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public Article save(User user, Article article) {
        article.setAuthorId(user.getId());
        return articleDao.save(article);
    }

    @Override
    public Article get(int id) {
        return articleDao.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(User user, int id) {
        checkPermission(user, id);
        articleDao.deleteById(id);
    }

    private void checkPermission(User user, int articleId) {
        Integer authorId = articleDao.getAuthorIdById(articleId);
        if (!user.getId().equals(authorId)) {
            throw new PermissionDeniedException("没有权限删除该文章");
        }
    }

    @Override
    public Article update(User user, int id, Article article) {
        checkPermission(user, id);
        article.setId(id);
        return articleDao.save(article);
    }

    @Override
    public Page<PageArticleDTO> getPage(@Nullable Boolean published, Pageable pageable) {
        Preconditions.checkArgument(pageable.getPageSize() < 100, "一次查询不能超过100篇文章");
        return published == null ? articleDao.findAllByPage(pageable) : articleDao.findAllByPage(published, pageable);
    }

    @Override
    public List<IdAndTitle> likeTitlePrefix(String titlePrefix, int size) {
        Preconditions.checkArgument(size < 100, "一次查询不能超过100篇文章");
        return articleDao.findByTitleStartingWith(titlePrefix, size);
    }
}
