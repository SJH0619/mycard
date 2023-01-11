package com.mycard.demo.user;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        if (!userCreateForm.getUserPwd1().equals(userCreateForm.getUserPwd2())) {
            bindingResult.rejectValue("userPwd2", "passwordIncorrect", "2개의 비밀번호가 일치하지 않습니다.");

            return "signup";
        }

        try {
            userService.createUser(userCreateForm.getUserId(), userCreateForm.getUserPwd1());
        } catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("idIncorrect", "이미 등록 된 아이디입니다.");
            
            return "signup";
        } catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            
            return "signup";
        }

        return "redirect:/signin";
    }

    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }
}
