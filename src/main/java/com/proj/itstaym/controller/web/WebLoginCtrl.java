package com.proj.itstaym.controller.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebLoginCtrl {

    @GetMapping({"/", "/login"})
    public String handleLoginAndRoot(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("redirecting!");
            return "redirect:/web/index";
        } else {
            System.out.println("not redirecting!");
        }
        return "login";
    }

    @GetMapping("/web/index")
    public String showMainPage() {
        System.out.println("I'll route you to main page!");
        return "index";
    }

}
