package cn.hff.blog.exception;

/**
 * 用户认证失败导致未授权
 * <p>
 * 对应{@link org.springframework.http.HttpStatus#UNAUTHORIZED} 401状态码
 * <p>
 * Created by Holmofy on 2018/6/19.
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
