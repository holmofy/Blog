package cn.hff.blog.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.hff.blog.common.Constants;
import cn.hff.blog.dao.UserDao;
import cn.hff.blog.entity.User;
import cn.hff.blog.exception.AuthenticationException;
import cn.hff.blog.mvc.WebMvcUtils;
import cn.hff.blog.service.UserService;
import cn.hff.blog.util.RegexUtil;

/**
 * 用户服务
 * <p>
 * Created by Holmofy on 2018/6/21.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User register(User user) {
        String encryptPassword = hashEncrypt(user.getPassword());
        user.setPassword(encryptPassword);
        return userDao.save(user);
    }

    private static final String salt = "LMbFvdCzTf7In1bJ";

    /**
     * 密码加盐，并对其进行11轮摘要加密
     *
     * @param credential 密码
     * @return 加密后的密码
     */
    private static String hashEncrypt(String credential) {
        String salted = credential + salt;
        byte[] bytes = salted.getBytes();
        for (int i = 0; i < 10; i++) {
            bytes = DigestUtils.md5Digest(bytes);
        }
        return DigestUtils.md5DigestAsHex(bytes);
    }

    /**
     * 用户登陆
     *
     * @param principal  身份，可以是用户名、邮箱、手机号等
     * @param credential 凭据，也就是密码
     */
    @Override
    public User login(String principal, String credential) {
        String encryptedCredential = hashEncrypt(credential);
        User example;
        if (RegexUtil.isEmail(principal)) {
            example = User.builder().email(principal).password(encryptedCredential).build();
        } else if (RegexUtil.isPhone(principal)) {
            example = User.builder().phone(principal).password(encryptedCredential).build();
        } else {
            example = User.builder().userName(principal).password(encryptedCredential).build();
        }
        User loginUser = userDao.findOne(Example.of(example)).orElseThrow(AuthenticationException::new);
        userDao.updateLastLoginTime(loginUser.getId(), LocalDateTime.now());
        WebMvcUtils.getSession().setAttribute(Constants.LOGIN_USER_SESSION_KEY, loginUser);
        return loginUser;
    }

    @Override
    public void logout(User user) {
        if (user != null && user.getId() != null) {
            // 只有当用户已经登陆，才能invalidate
            WebMvcUtils.getSession().invalidate();
        }
    }

}
