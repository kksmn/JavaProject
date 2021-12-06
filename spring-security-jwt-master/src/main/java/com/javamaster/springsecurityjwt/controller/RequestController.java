package com.javamaster.springsecurityjwt.controller;

import com.javamaster.springsecurityjwt.entity.RequestEntity;
import com.javamaster.springsecurityjwt.entity.Status;
import com.javamaster.springsecurityjwt.entity.UserEntity;
import com.javamaster.springsecurityjwt.exceptions.RequestException;
import com.javamaster.springsecurityjwt.service.RequestService;

import com.javamaster.springsecurityjwt.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;
    private static Logger logger;

    public RequestController(){
          logger = LoggerFactory.getLogger(RequestController.class);
    }

    @PostMapping("/createRequest")
    public String createRequest(@RequestBody @Valid NewRequest request) throws RequestException {
        RequestEntity r = new RequestEntity();

            r.setName(request.getName());
            r.setDescription(request.getDescription());

            if(requestService.saveRequest(r)){
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                Object pricipal = auth.getPrincipal();
                Integer user;
                if (pricipal instanceof UserEntity) {
                    user = ((UserEntity) pricipal).getId();
                }
                logger.info("New request has been created");
                return "OK";
            }

            else throw new RequestException("Incorrect name: such request already exists");

    }
    @PostMapping("/getByName")
    public RequestEntity getRequests(@RequestBody @Valid NewRequest request) throws RequestException {
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
    @GetMapping("/requests")
    public List<RequestEntity> getAllRequests() {
        List<RequestEntity> list=new ArrayList<RequestEntity>();
        list= requestService.getAll();
        return list;

    }
    @PutMapping("/user/{user_id}/request/{id}")
    public RequestEntity editRequest(@PathVariable Integer user_id,@PathVariable Integer id, @RequestBody @Valid NewRequest request) throws RequestException {

        RequestEntity requestEntity = new RequestEntity();
        Optional<RequestEntity> req = requestService.findById(id);
        if (req.isPresent()) {
            if (req.get().getUserEntity().getId() == user_id) {
                requestEntity.setId(id);
                requestEntity.setName(request.getName());
                requestEntity.setDescription(request.getDescription());
                requestService.updateById(id, requestEntity.getName(), requestEntity.getDescription());
                return requestEntity;

            } else throw new RequestException("Incorrect id");
        } else throw new RequestException("Such request doesn't exist");
    }



    }




