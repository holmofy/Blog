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
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonView;

import cn.hff.blog.dto.Views;
import lombok.Data;

/**
 * 文章实体类
 *
 * @author Holmofy
 */
@Data
@Entity
@Table(name = "tb_article")
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.WithoutLob.class)
    private Integer id;

    @NotNull
    @Size(max = 255, message = "标题长度不能超过255个字符")
    @JsonView(Views.WithoutLob.class)
    private String title;

    @CreatedDate
    @JsonView(Views.WithoutLob.class)
    private LocalDateTime created;

    @LastModifiedDate
    @JsonView(Views.WithoutLob.class)
    private LocalDateTime modified;

    @JsonView(Views.WithoutLob.class)
    private int views;

    /**
     * 可能会和{@link Comment}的条数有所差异，不过无所谓啦，这里效率重要
     */
    @JsonView(Views.WithoutLob.class)
    private int comments;

    @JsonView(Views.WithoutLob.class)
    private Integer categoryId;

    @JsonView(Views.WithoutLob.class)
    private int authorId;

    @JsonView(Views.WithoutLob.class)
    private boolean isDenyComment;

    @JsonView(Views.WithoutLob.class)
    private boolean published;

    @JsonView(Views.WithoutLob.class)
    private boolean deleted;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @JsonView(Views.WithLob.class)
    private String content;
}