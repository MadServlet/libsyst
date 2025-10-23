package com.proj.itstaym.controller.web;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/invalid")
public class WebInvalidUserCtrl {

    @GetMapping
    public String showInvalidPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("UNKNOWN");

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Dashboard");

        model.addAttribute("contentFragment", "fragments/error/unauthorized");
        model.addAttribute("status", "403");
        model.addAttribute("error", "Authority Not Verified");
        model.addAttribute("message", "Something went wrong with your account");


        return "template";
    }

}
