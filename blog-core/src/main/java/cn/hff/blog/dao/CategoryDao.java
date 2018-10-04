package cn.hff.blog.dao;

import org.springframework.data.jpa.repository.Query;

import cn.hff.blog.common.BaseJpaRepository;
import cn.hff.blog.entity.Category;

/**
 * {@link Category}的数据访问对象
 *
 * @author Holmofy
 */
public interface CategoryDao extends BaseJpaRepository<Category, Integer> {

    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
    @Query("select name from Category where id=?1")
    String getNameById(Integer categoryId);

}
