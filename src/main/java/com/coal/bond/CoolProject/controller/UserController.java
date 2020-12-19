package com.coal.bond.CoolProject.controller;

import com.coal.bond.CoolProject.domain.User;
import com.coal.bond.CoolProject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.coal.bond.CoolProject.form.UserForm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    @PostMapping("/signin")
    public String login(UserForm uf, HttpServletResponse response) {
        List<User> list = userService.list();
        for (User u : list) {
            if (u.getEmail().equals(uf.getEmail()) && u.getPassword().equals(uf.getPassword())) {
                Cookie cookie = new Cookie("id", u.getId().toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/";
            }
        }
        return "signinfail";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String register(UserForm userForm) {
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        userService.create(user);
        return "redirect:/";
    }

    @GetMapping("/signout")
    public String signout(HttpServletResponse response) {
        Cookie cookie = new Cookie("id", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
