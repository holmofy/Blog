package cn.hff.blog.entity;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/**
 * 文章评论实体类
 *
 * @author Holmofy
 */
@Data
@Entity
@Table(name = "tb_comment")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer articleId;

    private Integer replyId;

    private String name;

    private String email;

    private String ip;

    private String website;

    private Integer agree;

    private Integer disagree;

    @CreatedDate
    private LocalDateTime createTime;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;
}
