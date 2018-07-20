package cn.hff.blog.dao;

import cn.hff.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

/**
 * {@link User}的数据访问层
 * <p>
 * Created by Holmofy on 2018/6/18.
 */
public interface UserDao extends JpaRepository<User, Integer> {

    @Modifying
    @Query("update User set lastLoginTime=:time where id=:id")
    void updateLastLoginTime(@Param("id") Integer id, @Param("time") LocalDateTime time);

}
