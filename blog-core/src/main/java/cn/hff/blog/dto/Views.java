package cn.hff.blog.dto;


/**
 * 用Jackson的{@link com.fasterxml.jackson.annotation.JsonView}注解控制序列化的字段，
 * 可以省去很多DTO
 * 参考<a href="https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring">Spring Blog</>
 * <p>
 *
 * @see org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice
 * @see org.springframework.web.servlet.mvc.method.annotation.JsonViewRequestBodyAdvice
 */
public class Views {

    private Views() {
    }

    /**
     * 公开字段
     */
    public static interface Public {
    }

    /**
     * 内部字段
     */
    public static interface Internal extends Public {
    }

    /**
     * 不带大文本的字段
     */
    public static interface WithoutLob {
    }

    /**
     * 带大文本的字段
     */
    public static interface WithLob extends WithoutLob {
    }

}
