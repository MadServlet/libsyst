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
@RequestMapping("/web/librarian")
public class WebLibrarianCtrl {

    public final String PAGE_LABEL = "PTC Booklink: Library Management System";

    @GetMapping("/manage/books")
    public String showManageBooks(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("UNKNOWN");

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Manage Users");

        if (role.equals("LIBRARIAN")) {
            model.addAttribute("pageLabel", PAGE_LABEL);
            model.addAttribute("contentFragment", "fragments/pages/librarian/manage_books");
            model.addAttribute("cssFiles", List.of("/css/librarian/manage.books.css"));
            model.addAttribute("jsFiles", List.of("/js/librarian/manage.books.js"));
        } else {
            model.addAttribute("contentFragment", "fragments/error/unauthorized");
            model.addAttribute("status", "403");
            model.addAttribute("error", "Authority Not Verified");
            model.addAttribute("message", "Something went wrong with your account");
        }

        return "template";
    }

    @GetMapping("/manage/users")
    public String showManageUsers(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("UNKNOWN");

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Manage Users");

        if (role.equals("LIBRARIAN")) {
            model.addAttribute("pageLabel", PAGE_LABEL);
            model.addAttribute("contentFragment", "fragments/pages/librarian/manage_users");
            model.addAttribute("cssFiles", List.of("/css/librarian/manage.users.css"));
            model.addAttribute("jsFiles", List.of("/js/librarian/manage.users.js"));
        } else {
            model.addAttribute("contentFragment", "fragments/error/unauthorized");
            model.addAttribute("status", "403");
            model.addAttribute("error", "Authority Not Verified");
            model.addAttribute("message", "Something went wrong with your account");
        }

        return "template";
    }

    @GetMapping("/manage/circulation")
    public String showManageCirculation(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("UNKNOWN");

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Circulation");

        if (role.equals("LIBRARIAN")) {
            model.addAttribute("pageLabel", PAGE_LABEL);
            model.addAttribute("contentFragment", "fragments/pages/librarian/circulation");
            model.addAttribute("cssFiles", List.of("/css/librarian/circulation.css"));
            model.addAttribute("jsFiles", List.of("/js/librarian/circulation.js"));
        } else {
            model.addAttribute("contentFragment", "fragments/error/unauthorized");
            model.addAttribute("status", "403");
            model.addAttribute("error", "Authority Not Verified");
            model.addAttribute("message", "Something went wrong with your account");
        }

        return "template";
    }

    @GetMapping("/reports/lending")
    public String showReportsLoan(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("UNKNOWN");

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Borrow/Return History");

        if (role.equals("LIBRARIAN")) {
            model.addAttribute("pageLabel", PAGE_LABEL);
            model.addAttribute("contentFragment", "fragments/pages/librarian/borrow_return_history");
            model.addAttribute("cssFiles", List.of("/css/librarian/borrow.return.history.css"));
            model.addAttribute("jsFiles", List.of("/js/librarian/borrow.return.history.js"));
        } else {
            model.addAttribute("contentFragment", "fragments/error/unauthorized");
            model.addAttribute("status", "403");
            model.addAttribute("error", "Authority Not Verified");
            model.addAttribute("message", "Something went wrong with your account");
        }

        return "template";
    }

}
