package com.ebookmarket.controller;

import com.ebookmarket.domain.security.PasswordRestToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/account")
    public String account() {
        return "account";
    }

    @RequestMapping("/newAccount")
    public String newAccount(Model model) {
        model.addAttribute("classActiveNewAccount", true);
        return "account";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("classActiveLogin", true);
        return "account";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword(
            Locale locale,
            @RequestParam("token") String token,
            Model model) {
        PasswordRestToken passwordRestToken = userService.getPasswordRestToken(token);
        model.addAttribute("classActiveForgetPassword", true);
        return "account";
    }
}
