package com.pd.security.controller;

import com.pd.security.model.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * @author peramdy on 2018/5/21.
 */
@Controller
@RequestMapping("/shiro")
public class ShiroController {


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/html/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, UserInfo userInfo) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userInfo.getName(), userInfo.getPassword());
        try {
            SecurityUtils.getSubject().login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "/html/exception";
        }
        System.out.println("token：" + SecurityUtils.getSubject().getSession().getId());
        model.addAttribute("token", SecurityUtils.getSubject().getSession().getId());
        return "/html/success";
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "shiro");
        Session session = SecurityUtils.getSubject().getSession();
        System.out.println(session.getId());
        Collection<Object> collection = session.getAttributeKeys();
        for (Object obj : collection) {
            System.out.println(obj);
        }
        return "/html/hello";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        SecurityUtils.getSubject().logout();
        model.addAttribute("name", "shiro");
        return "/html/logout";
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public String exceptionHandler(Model model) {
        model.addAttribute("msg", "login fail!");
        return "/html/error";
    }

}
