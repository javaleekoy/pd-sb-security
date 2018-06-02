package com.pd.security.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author peramdy on 2018/5/18.
 */
@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "Dear");
        return "/html/hello";
    }

}
