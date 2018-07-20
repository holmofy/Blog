package cn.hff.blog.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 文章评论实体类
 * <p>
 * Created by Holmofy on 2018/6/18.
 */
@Data
@Entity
@Table(name = "tb_comment")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "article_id", nullable = false)
    private Integer articleId;

    @Column(name = "reply_id")
    private Integer replyId;

    private String name;

    private String email;

    private String ip;

    private String website;

    private Integer agree;

    private Integer disagree;

    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;
}
