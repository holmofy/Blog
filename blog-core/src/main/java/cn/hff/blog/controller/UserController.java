package cn.hff.blog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import cn.hff.blog.dto.Views;
import cn.hff.blog.entity.User;
import cn.hff.blog.mvc.CurrentUser;
import cn.hff.blog.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    @JsonView(Views.Public.class)
    public User register(@Valid @RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/api/session")
    @JsonView(Views.Public.class)
    public User currentUser(@CurrentUser User user) {
        return user;
    }

    @PostMapping("/api/session")
    @JsonView(Views.Public.class)
    public User login(@RequestParam String principal,
                      @RequestParam String credential) {
        return userService.login(principal, credential);
    }

    @DeleteMapping("/api/session")
    public void logout(@CurrentUser User user) {
        userService.logout(user);
    }

}
