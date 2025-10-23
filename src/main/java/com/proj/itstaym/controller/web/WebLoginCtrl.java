package com.proj.itstaym.controller.web;

import com.proj.itstaym.utils.CommonUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebLoginCtrl {

    @GetMapping({"/", "/login"})
    public String handleLoginAndRoot(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) return "redirect:/web/index";

        return "login";
    }

    @GetMapping("/web/index")
    public String showMainPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = CommonUtils.getRole(userDetails);

        return switch (role) {
            case "ADMIN" -> "redirect:/web/admin/dashboard";
            case "LIBRARIAN" -> "redirect:/web/librarian/dashboard";
            case "TEACHER", "STUDENT" -> "redirect:/web/user/dashboard";
            default -> "redirect:/web/invalid";
        };
    }

}
