package com.javamaster.springsecurityjwt.controller;

import com.javamaster.springsecurityjwt.entity.RequestEntity;
import com.javamaster.springsecurityjwt.entity.UserEntity;
import com.javamaster.springsecurityjwt.repository.RequestEntityRepository;
import com.javamaster.springsecurityjwt.repository.UserEntityRepository;
import com.javamaster.springsecurityjwt.service.RequestService;
import com.javamaster.springsecurityjwt.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestControllerTest {

    @Autowired
    private RequestService reqService;
    @MockBean
    private RequestEntityRepository requestEntityRepository;
    @Test
    public void createRequest() {
        RequestEntity req = new RequestEntity();
        req.setId(7);
        boolean isUserCreated = reqService.saveRequest(req);
        Assert.assertTrue(isUserCreated);
        Mockito.verify(requestEntityRepository, Mockito.times(1)).save(req);
    }

    @Test
    public void addReqFailTest() {
        RequestEntity req = new RequestEntity();
        req.setName("req");
        Mockito.doReturn(new RequestEntity())
                .when(requestEntityRepository)
                .findByName("req");

        Boolean isReqCreated = reqService.saveRequest(req);
        Assert.assertFalse(isReqCreated);
        Mockito.verify(requestEntityRepository, Mockito.times(0)).save(ArgumentMatchers.any(RequestEntity.class));

    }
}