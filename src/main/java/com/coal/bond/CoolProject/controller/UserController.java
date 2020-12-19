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

/**
 * User 컨트롤러
 * 이 class 안에서 모든 Mapping은 '/user' 뒤에 붙는다.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * JPA User Service
     */
    private UserService userService;

    /**
     * UserController initialize.
     * @param userService JPA User Service
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * signin 페이지를 렌더링하기 위한 함수.
     * @return signin 렌더링.
     */
    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    /**
     * 로그인 처리 함수.
     * @param userForm form 태그를 통해 전송받는 데이터 형식.
     * @param response HTTP 응답 변수.
     * @return 성공 시 Home으로 redirect, 실패 시 signinfail을 렌더링한다.
     */
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

    /**
     * signup 페이지를 렌더링하기 위한 함수.
     * @return signup 렌더링.
     */
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    /**
     * 회원가입 처리 함수.
     * @param userForm form 태그를 통해 전송받는 데이터 형식.
     * @return Home으로 redirect.
     */
    @PostMapping("/signup")
    public String register(UserForm userForm) {
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        userService.create(user);
        return "redirect:/";
    }

    /**
     * 로그아웃 처리 함수.
     * @param response HTTP 응답 변수로 쿠키 삭제를 위함.
     * @return Home으로 redirect.
     */
    @GetMapping("/signout")
    public String signout(HttpServletResponse response) {
        Cookie cookie = new Cookie("id", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    /**
     * list를 렌더링하기 위한 함수.
     * @param model list로 데이터를 넘겨주기 위함.
     * @return list 렌더링.
     */
    @GetMapping("/list")
    public String list(Model model) {
        List<User> users = userService.list();
        model.addAttribute("users", users);
        return "list";
    }

    /**
     * profile을 렌더링하기 위한 함수.
     * @param id PathVariable 형태의 Unique한 id값.
     * @param model profile로 데이터를 넘겨주기 위함.
     * @return User가 없다면 Home으로 redirect, 있다면 profile 렌더링.
     */
    @GetMapping("/profile/{id}")
    public String profile(@PathVariable long id, Model model) {
        Optional<User> user = userService.read(id);
        if (user.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("user", user.get());
        return "profile";
    }

    /**
     * User 정보 수정 함수.
     * @param id PathVariable 형태의 Unique한 id값.
     * @param userForm 수정할 데이터 형태.
     * @return list 페이지로 redirect.
     */
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

    /**
     * User 삭제 함수.
     * @param id 삭제할 id
     * @return list 페이지로 redirect.
     */
    @GetMapping("/delete/{id}")
    public String remove(@PathVariable long id) {
        userService.delete(id);
        return "redirect:/user/list";
    }
}
