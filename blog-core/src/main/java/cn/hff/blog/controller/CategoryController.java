package cn.hff.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hff.blog.entity.Category;
import cn.hff.blog.entity.User;
import cn.hff.blog.mvc.CurrentUser;
import cn.hff.blog.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> findAll(@CurrentUser User user) {
        return categoryService.findAll(user.getId());
    }

}
