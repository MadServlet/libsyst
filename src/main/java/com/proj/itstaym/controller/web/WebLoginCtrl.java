package com.proj.itstaym.controller.web;

import com.proj.itstaym.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebLoginCtrl {

//    private final UserService userService;
//
//    @Autowired
//    public WebLoginCtrl(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping({"/", "/login"})
    public String handleLoginAndRoot(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/web/index";
        }

        return "login";
    }

    @GetMapping("/web/index")
    public String showMainPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user_role", userDetails.getAuthorities().stream().iterator().next());
        return "index";
    }

}
