package com.javamaster.springsecurityjwt.controller;

import com.javamaster.springsecurityjwt.entity.RequestEntity;
import com.javamaster.springsecurityjwt.entity.UserEntity;
import com.javamaster.springsecurityjwt.repository.RequestEntityRepository;
import com.javamaster.springsecurityjwt.repository.UserEntityRepository;
import com.javamaster.springsecurityjwt.service.RequestService;
import com.javamaster.springsecurityjwt.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.*;

public class RequestControllerTest {

    @Autowired
    private RequestService reqService;
    @MockBean
    private RequestEntityRepository requestEntityRepository;
    @Test
    public void createRequest() {
        RequestEntity req = new RequestEntity();
        req.setName("req");
        boolean isUserCreated = reqService.saveRequest(req);
        Assert.assertTrue(isUserCreated);
        Mockito.verify(requestEntityRepository, Mockito.times(1)).save(req);
    }
}