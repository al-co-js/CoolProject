package com.coal.bond.CoolProject.controller;

import com.coal.bond.CoolProject.domain.User;
import com.coal.bond.CoolProject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {

    private UserService us;

    public UserController(UserService us) {
        this.us = us;
    }

    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    @PostMapping("/signin")
    public String login(UserForm uf, HttpServletResponse response) {
        List<User> list = us.list();
        for (User u : list) {
            if (u.getEmail().equals(uf.getEmail()) && u.getPassword().equals(uf.getPassword())) {
                Cookie cookie = new Cookie("id", u.getId().toString());
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
    public String register(UserForm uf) {
        User u = new User();
        u.setName(uf.getName());
        u.setEmail(uf.getEmail());
        u.setPassword(uf.getPassword());
        us.create(u);
        return "redirect:/";
    }

}
