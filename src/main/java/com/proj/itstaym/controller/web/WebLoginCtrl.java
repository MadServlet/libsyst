package com.proj.itstaym.controller.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebLoginCtrl {

    @GetMapping({"/", "/login"})
    public String handleLoginAndRoot(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/web/index";
        }

        return "login";
    }

    @GetMapping("/web/index")
    public String showMainPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("UNKNOWN");

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Dashboard");



        switch (role) {
            case "ADMIN":
                model.addAttribute("contentFragment", "fragments/dashboards/admin");
                model.addAttribute("cssFiles", List.of("/css/admin/user.creation.css"));
                model.addAttribute("jsFiles", List.of("/js/admin/user.creation.js"));
                break;

            case "LIBRARIAN":
                model.addAttribute("contentFragment", "fragments/dashboards/librarian");
                model.addAttribute("cssFiles", List.of("/css/librarian/dashboard.css"));
                model.addAttribute("jsFiles", List.of("/js/librarian/dashboard.js"));
                break;
            case "TEACHER":
            case "STUDENT":
                model.addAttribute("contentFragment", "fragments/dashboards/student");
                model.addAttribute("cssFiles", List.of("/css/student/dashboard.css"));
                model.addAttribute("jsFiles", List.of("/js/student/dashboard.js"));
                break;

            default:
                model.addAttribute("contentFragment", "fragments/error/unauthorized");
                model.addAttribute("status", "403");
                model.addAttribute("error", "Authority Not Verified");
                model.addAttribute("message", "Something went wrong with your account");
                break;
        }


        return "template";
    }

}
