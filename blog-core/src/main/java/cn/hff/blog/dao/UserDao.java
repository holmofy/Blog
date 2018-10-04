package cn.hff.blog.dao;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.hff.blog.common.BaseJpaRepository;
import cn.hff.blog.entity.User;

/**
 * {@link User}的数据访问层
 *
 * @author Holmofy
 */
public interface UserDao extends BaseJpaRepository<User, Integer> {

    @Modifying
    @Query("update User set lastLoginTime=:time where id=:id")
    void updateLastLoginTime(@Param("id") Integer id, @Param("time") LocalDateTime time);

}
