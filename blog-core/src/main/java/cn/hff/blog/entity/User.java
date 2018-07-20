package cn.hff.blog.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonView;

import cn.hff.blog.dto.Views;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 * <p>
 * Created by Holmofy on 2018/6/18.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    private Integer id;

    @NotNull(message = "用户名不能为空")
    @Column(name = "user_name")
    @JsonView(Views.Public.class)
    private String userName;

    @Column(unique = true)
    @JsonView(Views.Public.class)
    private String email;

    @Column(unique = true)
    @JsonView(Views.Public.class)
    private String phone;

    /**
     * 虽然已经加密过，密码不要序列化到客户端
     */
    @JsonView(Views.Internal.class)
    @NotNull(message = "密码不能为空")
    @Column(name = "passwd_md5")
    private String password;

    @JsonView(Views.Public.class)
    @Column(name = "nick_name")
    private String nickName;

    @JsonView(Views.Public.class)
    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @JsonView(Views.Public.class)
    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @JsonView(Views.Public.class)
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;
}
