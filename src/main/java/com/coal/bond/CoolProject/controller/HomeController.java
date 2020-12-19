package com.coal.bond.CoolProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(@CookieValue(name = "id", defaultValue = "null") String id, Model model) {
        model.addAttribute("id", id);
        return "home";
    }
}
