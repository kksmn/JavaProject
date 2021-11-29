package com.javamaster.springsecurityjwt.controller;

import com.javamaster.springsecurityjwt.entity.RequestEntity;
import com.javamaster.springsecurityjwt.entity.Status;
import com.javamaster.springsecurityjwt.service.RequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RestController
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PostMapping("/createRequest")
    public String registerUser(@RequestBody @Valid NewRequest request) {
        RequestEntity r = new RequestEntity();
        if(requestService.findByName(request.getName())==null){
            r.setName(request.getName());
            r.setDescription(request.getDescription());
            requestService.saveRequest(r);

            return "OK";
        }
        else return "Such request already exists";
    }
    @PostMapping("/getByName")
    public RequestEntity getRequests(@RequestBody @Valid NewRequest request) {
        if(requestService.findByName(request.getName())!=null){
            RequestEntity req=new RequestEntity();
            req=requestService.findByName(request.getName());

            return req;
       }
        else return null;
    }

    /*@GetMapping("/request/{id}/approve")
    public String approveRequest(@PathVariable Integer id) {
       requestService.findById(id);
       return "ok";

    }*/
    @GetMapping("/request/{id}/decline")
    public String declineRequest(@PathVariable Integer id) {
        requestService.deleteById(id);
        return "ok";

    }
    /*@GetMapping("/requests")
    public List<RequestEntity> getAllRequests() {
        List<RequestEntity> list=new ArrayList<RequestEntity>();
        list= requestService.getAll();
        return list;

    }*/



}
