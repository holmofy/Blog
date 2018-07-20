package cn.hff.blog.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * SpringMVC工具类
 * <p>
 * Created by Holmofy on 2018/6/20.
 */
@Slf4j
public class WebMvcUtils {

    private WebMvcUtils() {
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    public static HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession();
        } catch (Exception e) {
            log.error("Session获取失败", e);
        }
        return session;
    }

    /**
     * 获取IP地址
     * X-Real-IP需要RealIP模块的支持
     * 参考：http://nginx.org/en/docs/http/ngx_http_realip_module.html
     * 参考RFC7239:https://tools.ietf.org/html/rfc7239
     */
    public static String getIp() {
        final String unknown = "unknown";
        // NGINX反向代理IP
        String ip = getRequest().getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = getRequest().getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取HTTP头信息
     *
     * @return Http Header
     */
    public static String getHeader(String name) {
        return getRequest().getHeader(name);
    }

    /**
     * 获取客户端浏览器代理
     *
     * @return 浏览器信息
     */
    public static String getAgent() {
        return getHeader(HttpHeaders.USER_AGENT);
    }

}
