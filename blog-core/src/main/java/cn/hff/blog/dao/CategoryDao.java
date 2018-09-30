package cn.hff.blog.dao;

import cn.hff.blog.entity.Category;

import org.springframework.data.jpa.repository.Query;

/**
 * {@link Category}的数据访问对象
 * <p>
 * Created by Holmofy on 2018/6/18.
 */
public interface CategoryDao extends BaseJpaRepository<Category, Integer> {

    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
    @Query("select name from Category where id=?1")
    String getNameById(Integer categoryId);

}
