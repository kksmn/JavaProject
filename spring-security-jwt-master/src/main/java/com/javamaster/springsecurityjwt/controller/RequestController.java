package com.javamaster.springsecurityjwt.controller;

import com.javamaster.springsecurityjwt.entity.RequestEntity;
import com.javamaster.springsecurityjwt.entity.Status;
import com.javamaster.springsecurityjwt.entity.UserEntity;
import com.javamaster.springsecurityjwt.exceptions.RequestException;
import com.javamaster.springsecurityjwt.exceptions.UserException;
import com.javamaster.springsecurityjwt.service.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Controller
public class RequestController {

    @Autowired
    private RequestService requestService;
    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("D:\\spring-security-jwt-master\\spring-security-jwt-master\\src\\main\\resources\\log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(RequestController.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    @PostMapping("/createRequest")
    public ModelAndView createRequest( @Valid NewRequest request) throws RequestException {
        RequestEntity r = new RequestEntity();

            r.setName(request.getName());
            r.setDescription(request.getDescription());
            r.setUserEntity(UserEntity.currentUser);

            if(requestService.saveRequest(r)){
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                Object pricipal = auth.getPrincipal();
                Integer user;
                if (pricipal instanceof UserEntity) {
                    user = ((UserEntity) pricipal).getId();
                }
                LOGGER.log(Level.INFO,"New request has been created");
                List<RequestEntity> list=new ArrayList<RequestEntity>();
                list= requestService.getAll();
                ModelAndView modelAndView = new ModelAndView("index");
                modelAndView.addObject("list",list);
                return modelAndView;
            }

            else throw new RequestException("Incorrect name: such request already exists");

    }

    @PostMapping("/getByName")
    public RequestEntity getRequests( @Valid NewRequest request) throws RequestException {
        if(requestService.findByName(request.getName())!=null){
            RequestEntity req=new RequestEntity();
            req=requestService.findByName(request.getName());

            return req;
       }
        else throw new RequestException("Incorrect name: such request doesnt exists");
    }

    @GetMapping("/request/{id}/approve")
    public String approveRequest(@PathVariable Integer id) throws RequestException {
        if( id!=null && id>0) {
            requestService.approveById(id);
            return "ok";
        }
        else throw new RequestException("Incorrect id");

    }
    @GetMapping("/request/{id}/decline")
    public String declineRequest(@PathVariable Integer id) throws RequestException {
        if( id!=null && id>0) {
            requestService.deleteById(id);
            return "ok";
        }
        else throw new RequestException("Incorrect id");

    }
    @Retryable(value = UserException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping("/request/{name}")
    public ModelAndView getEditRequest(@PathVariable String name) throws RequestException, UserException {

        RequestEntity requestEntity = new RequestEntity();
        RequestEntity req=requestService.findByName(name);
        if(!(req.getUserEntity().equals(UserEntity.currentUser))){
            throw new UserException("Not your article");
        }
        else {
            ModelAndView modelAndView = new ModelAndView("editreq");
            modelAndView.addObject("request", req);
            return modelAndView;
        }
    }
    @Retryable(value = UserException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping("/delete/{name}")
    public String deleteRequest(@PathVariable String name) throws RequestException, UserException {

        RequestEntity req=requestService.findByName(name);
        if(!(UserEntity.currentUser.getRoles().equals("ROLE_ADMIN"))){
            throw new UserException("You are not admin");
        }
        else {
          requestService.deleteById(req.getId());
        }
        return "redirect:/getCollection";
    }
    @Recover
    private ModelAndView recoverObject(Throwable throwable){
        ModelAndView view = new ModelAndView();
        view.setViewName("error");
        System.out.println(throwable.getMessage());
        return view;
    }
    @PostMapping("/editRequest/{id}")
    public String editRequest(@PathVariable Integer id, NewRequest request) throws RequestException {
        Optional<RequestEntity> requestEntity=requestService.findById(id);
        if(requestEntity.isPresent()){
            RequestEntity req=requestEntity.get();
            requestService.updateById(id,request.getName(),request.getDescription());
        }

        return "redirect:/getCollection";
    }
    @RequestMapping(value = { "/getCollection"}, method = RequestMethod.GET)
    public ModelAndView reqlist(Model model) {
        List<RequestEntity> list=new ArrayList<RequestEntity>();
        list= requestService.getAll();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("list",list);
        return modelAndView;
    }



    }




