package cn.hff.blog.service;

import cn.hff.blog.entity.User;

/**
 * 用户
 * <p>
 * Created by Holmofy on 2018/6/21.
 */
public interface UserService {

    User register(User user);

    User login(String username, String password);

    void logout(User user);

}
