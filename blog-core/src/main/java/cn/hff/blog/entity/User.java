package cn.hff.blog.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
 *
 * @author Holmofy
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user", uniqueConstraints = {
        @UniqueConstraint(name = "uniq_email", columnNames = "email"),
        @UniqueConstraint(name = "uniq_phone", columnNames = "phone")
})
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    private Integer id;

    @NotNull(message = "用户名不能为空")
    @Column(name = "user_name")
    @JsonView(Views.Public.class)
    private String username;

    @Column
    @JsonView(Views.Public.class)
    private String email;

    @Column
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
    private String nickName;

    @JsonView(Views.Public.class)
    @CreatedDate
    private LocalDateTime created;

    @JsonView(Views.Public.class)
    @LastModifiedDate
    private LocalDateTime modified;

    @JsonView(Views.Public.class)
    private LocalDateTime lastLoginTime;
}
