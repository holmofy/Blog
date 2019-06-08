package cn.hff.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hff.blog.dao.CategoryDao;
import cn.hff.blog.entity.Category;
import cn.hff.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> findAll(int userId) {
        return categoryDao.findByUserId(userId);
    }

}
