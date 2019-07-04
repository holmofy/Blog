package cn.hff.blog.dto;

import lombok.Data;

/**
 * @author holmofy
 */
@Data
public class ArticleSearchDto {

    /**
     * @see cn.hff.blog.entity.Article#published
     */
    private Boolean published;

    /**
     * @see cn.hff.blog.entity.Article#deleted
     */
    private Boolean deleted;

}
