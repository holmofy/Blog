package cn.hff.blog.mvc;

import java.lang.annotation.*;

/**
 * 在Controller方法的参数中标记当前用户
 * <p>
 * Created by Holmofy on 2018/6/18.
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CurrentUser {
}
