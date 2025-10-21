package com.proj.itstaym.controller.web;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web/user")
public class WebUserCtrl {

    public final String PAGE_LABEL = "PTC Booklink: Library Management System";

    @GetMapping("/reports/lending")
    public String showLendingReport(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("UNKNOWN");

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Borrow/Return History");

        if (role.equals("STUDENT") || role.equals("TEACHER")) {
            model.addAttribute("pageLabel", PAGE_LABEL);
            model.addAttribute("contentFragment", "fragments/pages/user/borrow_return_history");
            model.addAttribute("cssFiles", List.of("/css/user/borrow.return.history.css"));
            model.addAttribute("jsFiles", List.of("/js/user/borrow.return.history.js"));
        } else {
            model.addAttribute("contentFragment", "fragments/error/unauthorized");
            model.addAttribute("status", "403");
            model.addAttribute("error", "Authority Not Verified");
            model.addAttribute("message", "Something went wrong with your account");
        }

        return "template";
    }

}
