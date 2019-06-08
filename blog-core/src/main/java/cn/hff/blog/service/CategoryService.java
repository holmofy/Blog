package cn.hff.blog.service;

import java.util.List;

import cn.hff.blog.entity.Category;

/**
 * 文章分类服务
 *
 * @author holmofy
 */
public interface CategoryService {

    List<Category> findAll(int userId);
}
