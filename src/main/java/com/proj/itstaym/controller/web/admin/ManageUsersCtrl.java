package com.proj.itstaym.controller.web.admin;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web/admin")
public class ManageUsersCtrl {

    @GetMapping("/manage/users")
    public String showManageUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("UNKNOWN");

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Manage Users");

        if (role.equals("ADMIN")) {
            model.addAttribute("pageLabel", "Admin");
            model.addAttribute("contentFragment", "fragments/pages/admin/manage_users");
            model.addAttribute("cssFiles", List.of("/css/admin/manage.users.css"));
            model.addAttribute("jsFiles", List.of("/js/admin/manage.users.js"));
        } else {
            model.addAttribute("contentFragment", "fragments/error/unauthorized");
            model.addAttribute("status", "403");
            model.addAttribute("error", "Authority Not Verified");
            model.addAttribute("message", "Something went wrong with your account");
        }

        return "template";
    }

}
