package com.javamaster.springsecurityjwt.controller;

import com.javamaster.springsecurityjwt.entity.RequestEntity;
import com.javamaster.springsecurityjwt.entity.UserEntity;
import com.javamaster.springsecurityjwt.exceptions.RequestException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Tag(name = "User", description = "The User API")
@RestController
public class ArticleController {
    @PostMapping("/create")
    public ModelAndView createArt(@RequestBody @Valid NewRequest request) throws RequestException {
        RequestEntity r = new RequestEntity();

        r.setName(request.getName());
        r.setDescription(request.getDescription());
        r.setUserEntity(UserEntity.currentUser);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Object pricipal = auth.getPrincipal();
            Integer user;
            if (pricipal instanceof UserEntity) {
                user = ((UserEntity) pricipal).getId();
            }
            List<RequestEntity> list=new ArrayList<RequestEntity>();
            ModelAndView modelAndView = new ModelAndView("index");
            modelAndView.addObject("list",list);
            return modelAndView;


    }
    @PostMapping("/editRequest/{id}")
    public ModelAndView editRequest(@PathVariable Integer id,@RequestBody  NewRequest request) throws RequestException {
        List<RequestEntity> list=new ArrayList<RequestEntity>();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("list",list);
        return modelAndView;
    }
}
