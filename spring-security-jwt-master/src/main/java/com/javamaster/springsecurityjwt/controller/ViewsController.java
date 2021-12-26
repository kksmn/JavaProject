package com.javamaster.springsecurityjwt.controller;

import com.javamaster.springsecurityjwt.entity.RequestEntity;
import com.javamaster.springsecurityjwt.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewsController {
    @Autowired
    private RequestService requestService;

    @GetMapping(value = "/register")
    public ModelAndView Login(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
    @GetMapping(value = "/index")
    public ModelAndView GetMainPage(Model model) {
        List<RequestEntity> list=new ArrayList<RequestEntity>();
        list= requestService.getAll();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("list",list);
        return modelAndView;
    }
    @GetMapping(value = "/auth")
    public ModelAndView Auth(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth");
        return modelAndView;
    }
    @GetMapping(value = "/editreq")
    public ModelAndView AdminPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editreq");
        return modelAndView;
    }

    @GetMapping(value = "/getByName")
    public ModelAndView DoctorPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("getRequest");
        return modelAndView;
    }
    @GetMapping(value = "/createRequest")
    public ModelAndView CreateRequest(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("createRequest");
        return modelAndView;
    }



}
