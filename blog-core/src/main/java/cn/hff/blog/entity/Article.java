package cn.hff.blog.entity;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章实体类
 * <p>
 * Created by Holmofy on 2018/6/18.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "create_time")
    @JsonView(Views.WithoutLob.class)
    private LocalDateTime createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    @JsonView(Views.WithoutLob.class)
    private LocalDateTime updateTime;

    @JsonView(Views.WithoutLob.class)
    private Integer views;

    /**
     * 可能会和{@link Comment}的条数有所差异，不过无所谓啦，这里效率重要
     */
    @JsonView(Views.WithoutLob.class)
    private Integer comments;

    @Column(name = "category_id")
    @JsonView(Views.WithoutLob.class)
    private Integer categoryId;

    @Column(name = "author_id")
    @JsonView(Views.WithoutLob.class)
    private Integer authorId;

    @Column(name = "is_deny_comment")
    @JsonView(Views.WithoutLob.class)
    private Boolean isDenyComment;

    @JsonView(Views.WithoutLob.class)
    private Boolean published;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @JsonView(Views.WithLob.class)
    private String content;
}