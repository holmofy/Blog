package cn.hff.blog.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;

import cn.hff.blog.entity.Article;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章内容是大文本类型而且设置成了懒加载，
 * 获取文章分页时不需要文章内容，
 * 这样可以降低数据库压力，同时减少网络传输
 * <p>
 * Created by Holmofy on 2018/6/18.
 */
@Data
public class PageArticleDTO {

    /**
     * 除去{@link Article#content}这个大文本字段
     */
    private Integer id;
    private String title;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer views;
    private Integer comments;
    private Integer categoryId;
    private Integer authorId;
    /**
     * 这个字段还是用查询关联一下
     */
    private String categoryName;

    /**
     * {@link cn.hff.blog.dao.ArticleDao#findAllByPage(Pageable)}返回要用到这个构造函数
     * <p>
     * 注意参数顺序要和查询语句中的顺序一致
     */
    public PageArticleDTO(Integer id, String title,
                          LocalDateTime createTime, LocalDateTime updateTime,
                          Integer views, Integer comments, Integer categoryId,
                          Integer authorId, String categoryName) {
        this.id = id;
        this.title = title;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.views = views;
        this.comments = comments;
        this.categoryId = categoryId;
        this.authorId = authorId;
        this.categoryName = categoryName;
    }
}
