package cn.hff.blog.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.hff.blog.exception.AuthenticationException;
import cn.hff.blog.exception.NotFoundException;
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
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public String authenticationExceptionHandler(AuthenticationException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PermissionDeniedException.class)
    public String permissionDeniedExceptionHandler(PermissionDeniedException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String notFoundException(NotFoundException e) {
        return e.getMessage();
    }

}
