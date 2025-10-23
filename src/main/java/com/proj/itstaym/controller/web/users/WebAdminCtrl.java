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
@RequestMapping("/web/admin")
public class WebAdminCtrl {

    @GetMapping("/dashboard")
    public String showDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = CommonUtils.getRole(userDetails);
        if (!role.equals("ADMIN")) return "redirect:/web/invalid";

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Admin - Create User Account");
        model.addAttribute("pageLabel", "Admin");
        model.addAttribute("contentFragment", "fragments/pages/admin/admin_dashboard");
        model.addAttribute("cssFiles", List.of("/css/admin/admin.user.creation.css"));
        model.addAttribute("jsFiles", List.of("/js/admin/admin.user.creation.js"));


        return "template";
    }

    @GetMapping("/manage/users")
    public String showManageUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = CommonUtils.getRole(userDetails);
        if (!role.equals("ADMIN")) return "redirect:/web/invalid";

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Admin - User Management");
        model.addAttribute("pageLabel", "Admin");
        model.addAttribute("contentFragment", "fragments/pages/admin/admin_manage_users");
        model.addAttribute("cssFiles", List.of("/css/admin/admin.manage.users.css"));
        model.addAttribute("jsFiles", List.of("/js/admin/admin.manage.users.js"));

        return "template";
    }

    @GetMapping("/reports/lending")
    public String showLendingReport(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        var role = CommonUtils.getRole(userDetails);

        if (!role.equals("ADMIN")) return "redirect:/web/invalid";

        model.addAttribute("user_role", role);
        model.addAttribute("pageTitle", "Borrow/Return History");
        model.addAttribute("pageLabel", "Admin");
        model.addAttribute("contentTitle", "User Borrow/Return History");
        model.addAttribute("contentFragment", "fragments/pages/generic/generic_borrow_return_history");
        model.addAttribute("cssFiles", List.of("/css/generic/generic.borrow.return.history.css"));
        model.addAttribute("jsFiles", List.of("/js/generic/generic.borrow.return.history.js"));

        return "template";
    }

}
