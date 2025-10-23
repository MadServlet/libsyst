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
@RequestMapping("/web/user")
public class WebUserCtrl {

    public final String PAGE_LABEL = "PTC Booklink: Library Management System";

    @GetMapping("/dashboard")
    public String showDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = CommonUtils.getRole(userDetails);
        if (!role.equals("TEACHER") && !role.equals("STUDENT")) return "redirect:/web/invalid";

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Library Management System - Dashboard");
        model.addAttribute("pageLabel", PAGE_LABEL);
        model.addAttribute("contentFragment", "fragments/pages/user/user_dashboard");
        model.addAttribute("cssFiles", List.of("/css/user/dashboard.css"));
        model.addAttribute("jsFiles", List.of("/js/user/dashboard.js"));

        return "template";
    }

    @GetMapping("/reports/lending")
    public String showLendingReport(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = CommonUtils.getRole(userDetails);
        if (!role.equals("TEACHER") && !role.equals("STUDENT")) return "redirect:/web/invalid";

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Borrowed/Returned History Books");
        model.addAttribute("pageLabel", PAGE_LABEL);
        model.addAttribute("contentTitle", "Borrow/Return History");
        model.addAttribute("contentFragment", "fragments/pages/generic/borrow_return_history");
        model.addAttribute("cssFiles", List.of("/css/generic/borrow.return.history.css"));
        model.addAttribute("jsFiles", List.of("/js/generic/borrow.return.history.js"));

        return "template";
    }

}
