package cn.hff.blog.exception;

/**
 * 资源未找到
 * <p>
 * Created by Holmofy on 2018/7/21.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
