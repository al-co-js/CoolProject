package com.coal.bond.CoolProject.controller;

import com.coal.bond.CoolProject.domain.User;
import com.coal.bond.CoolProject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.coal.bond.CoolProject.form.UserForm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

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
    public String login(UserForm userForm, HttpServletResponse response) {
        List<User> list = userService.list();
        for (User user : list) {
            if (user.getEmail().equals(userForm.getEmail()) && user.getPassword().equals(userForm.getPassword())) {
                Cookie cookie = new Cookie("id", user.getId().toString());
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

    @GetMapping("/list")
    public String list(Model model) {
        List<User> users = userService.list();
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable long id, Model model) {
        Optional<User> user = userService.read(id);
        if (user.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("user", user.get());
        return "profile";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable long id, UserForm userForm) {
        Optional<User> fetchedUser = userService.read(id);
        if (fetchedUser.isEmpty()) {
            return "redirect:/";
        }

        User user = fetchedUser.get();
        if (!userForm.getName().equals("")) {
            user.setName(userForm.getName());
        }
        if (!userForm.getEmail().equals("")) {
            user.setEmail(userForm.getEmail());
        }
        userService.create(user);
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String remove(@PathVariable long id) {
        userService.delete(id);
        return "redirect:/user/list";
    }
}
