package cn.hff.blog.mvc;

import cn.hff.blog.common.Constants;
import cn.hff.blog.exception.AuthenticationException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * {@link @CurrentUser}注解解析器
 * <p>
 * Created by Holmofy on 2018/6/19.
 */
public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(CurrentUser.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object user = WebMvcUtils.getSession().getAttribute(Constants.LOGIN_USER_SESSION_KEY);
        if (user == null) {
            throw new AuthenticationException("没有权限，请先登录");
        }
        return user;
    }
}
