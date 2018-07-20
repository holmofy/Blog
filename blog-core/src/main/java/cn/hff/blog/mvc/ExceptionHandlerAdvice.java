package cn.hff.blog.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.hff.blog.exception.AuthenticationException;
import cn.hff.blog.exception.PermissionDeniedException;

/**
 * 负责处理Controller抛出的错误
 * <p>
 * Created by Holmofy on 2018/6/19.
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * 主要处理{@link com.google.common.base.Preconditions}参数校验的异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public void illegalArgumentExceptionHandler() {
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = AuthenticationException.class)
    public void AuthenticationExceptionHandler() {
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = PermissionDeniedException.class)
    public void PermissionDeniedExceptionHandler() {

    }
}
