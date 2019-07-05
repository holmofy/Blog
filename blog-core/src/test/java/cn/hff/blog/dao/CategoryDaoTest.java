package cn.hff.blog.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hff.blog.BaseDaoTest;
import cn.hff.blog.entity.Category;

/**
 * @author holmofy
 */
public class CategoryDaoTest extends BaseDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void testCategoryDao() {
        Category category = new Category();
        category.setName("分类一");
        Category save = categoryDao.save(category);
        String name = categoryDao.getNameById(save.getId());
        Assert.assertEquals(name, "分类一");
    }

}
