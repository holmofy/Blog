package cn.hff.blog.exception;

/**
 * 用户操作没有权限
 * <p>
 * 对应{@link org.springframework.http.HttpStatus#FORBIDDEN} 403状态码
 * <p>
 * 区别401状态码：知道你是谁，但是你就是没有权限
 * <p>
 * Created by Holmofy on 2018/6/23.
 */
public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException() {
    }

    public PermissionDeniedException(String message) {
        super(message);
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
