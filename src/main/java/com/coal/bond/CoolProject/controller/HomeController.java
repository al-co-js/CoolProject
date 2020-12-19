package com.coal.bond.CoolProject.controller;

import com.coal.bond.CoolProject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private UserService us;

    public HomeController(UserService us) {
        this.us = us;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
    }
