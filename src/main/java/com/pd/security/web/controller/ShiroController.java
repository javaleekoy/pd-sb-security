package com.pd.security.web.controller;

import com.pd.security.web.dto.UserDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import java.util.Collection;

/**
 * @author peramdy on 2018/5/21.
 */
@Controller
@RequestMapping("/shiro")
public class ShiroController {


    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String index() {
        return "/html/login";
    }

    /**
     * 登录验证
     *
     * @param model
     * @param userDto
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, UserDto userDto, ServletRequest request) {
        UsernamePasswordToken token = new UsernamePasswordToken(userDto.getName(), userDto.getPassword());



        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
//            return "/html/exception";
        }
        System.out.println(request.getParameter("shiroLoginFailure"));

        System.out.println("token：" + SecurityUtils.getSubject().getSession().getId());
        model.addAttribute("token", SecurityUtils.getSubject().getSession().getId());
        return "/html/success";
    }

    @RequiresPermissions("pd:shiro:view")
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

    /*@GetMapping("/logout")
    public String logout(Model model) {
        SecurityUtils.getSubject().logout();
        model.addAttribute("name", "shiro");
        return "/html/logout";
    }*/

    @ExceptionHandler(IncorrectCredentialsException.class)
    public String exceptionHandler(Model model) {
        model.addAttribute("msg", "login fail!");
        return "/html/error";
    }

}
