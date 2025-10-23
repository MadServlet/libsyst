package com.proj.itstaym.controller.web.users;

import com.proj.itstaym.utils.CommonUtils;
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

    @GetMapping("/dashboard")
    public String showDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = CommonUtils.getRole(userDetails);
        if (!role.equals("LIBRARIAN")) return "redirect:/web/invalid";

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Library Management System - Dashboard");
        model.addAttribute("pageLabel", PAGE_LABEL);
        model.addAttribute("contentFragment", "fragments/pages/librarian/librarian_dashboard");
        model.addAttribute("cssFiles", List.of("/css/librarian/dashboard.css"));
        model.addAttribute("jsFiles", List.of("/js/librarian/dashboard.js"));

        return "template";
    }

    @GetMapping("/manage/books")
    public String showManageBooks(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = CommonUtils.getRole(userDetails);
        if (!role.equals("LIBRARIAN")) return "redirect:/web/invalid";

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Library Management System - Books");
        model.addAttribute("pageLabel", PAGE_LABEL);
        model.addAttribute("contentFragment", "fragments/pages/librarian/manage_books");
        model.addAttribute("cssFiles", List.of("/css/librarian/manage.books.css"));
        model.addAttribute("jsFiles", List.of("/js/librarian/manage.books.js"));

        return "template";
    }

    @GetMapping("/manage/users")
    public String showManageUsers(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = CommonUtils.getRole(userDetails);
        if (!role.equals("LIBRARIAN")) return "redirect:/web/invalid";

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Library Management System - Users");
        model.addAttribute("pageLabel", PAGE_LABEL);
        model.addAttribute("contentFragment", "fragments/pages/librarian/manage_users");
        model.addAttribute("cssFiles", List.of("/css/librarian/manage.users.css"));
        model.addAttribute("jsFiles", List.of("/js/librarian/manage.users.js"));


        return "template";
    }

    @GetMapping("/manage/circulation")
    public String showManageCirculation(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = CommonUtils.getRole(userDetails);
        if (!role.equals("LIBRARIAN")) return "redirect:/web/invalid";

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Library Management System - Borrow & Return Books");
        model.addAttribute("pageLabel", PAGE_LABEL);
        model.addAttribute("contentFragment", "fragments/pages/librarian/circulation");
        model.addAttribute("cssFiles", List.of("/css/librarian/circulation.css"));
        model.addAttribute("jsFiles", List.of("/js/librarian/circulation.js"));

        return "template";
    }

    @GetMapping("/reports/lending")
    public String showReportsLoan(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = CommonUtils.getRole(userDetails);
        if (!role.equals("LIBRARIAN")) return "redirect:/web/invalid";

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Borrow/Return History");
        model.addAttribute("pageLabel", PAGE_LABEL);
        model.addAttribute("contentTitle", "User Borrow/Return History");
        model.addAttribute("contentFragment", "fragments/pages/generic/borrow_return_history");
        model.addAttribute("cssFiles", List.of("/css/generic/borrow.return.history.css"));
        model.addAttribute("jsFiles", List.of("/js/generic/borrow.return.history.js"));

        return "template";
    }

}
