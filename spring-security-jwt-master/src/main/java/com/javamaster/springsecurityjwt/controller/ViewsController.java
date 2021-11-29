package com.javamaster.springsecurityjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewsController {
    @GetMapping(value = {"/", "/index"})
    public ModelAndView Index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @GetMapping(value = "/register")
    public ModelAndView Login(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
    @GetMapping(value = "/admin-page")
    public ModelAndView AdminPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin-page");
        return modelAndView;
    }
    @GetMapping(value = "/doctor-page")
    public ModelAndView DoctorPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("doctor-page");
        return modelAndView;
    }
}
