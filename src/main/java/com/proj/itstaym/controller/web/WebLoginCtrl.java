package com.proj.itstaym.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebLoginCtrl {

    @GetMapping
    public String showHomePage() {
        return "login";
    }

    @GetMapping("/web/index")
    public String showMainPage() {

        return "index";
    }

}
