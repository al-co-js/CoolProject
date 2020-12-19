package com.coal.bond.CoolProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home 컨트롤러
 */
@Controller
public class HomeController {
    /**
     * home 페이지를 렌더링하기 위한 함수.
     * @param id Cookie중 id라는 이름을 가진 것을 가져옴.
     * @param model home에 데이터를 넘겨주기 위함.
     * @return home을 렌더링한다.
     */
    @GetMapping("/")
    public String home(@CookieValue(name = "id", defaultValue = "null") String id, Model model) {
        model.addAttribute("id", id);
        return "home";
    }
}
