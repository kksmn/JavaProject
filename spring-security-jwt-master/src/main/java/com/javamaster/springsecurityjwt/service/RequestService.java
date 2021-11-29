package com.javamaster.springsecurityjwt.service;

import com.javamaster.springsecurityjwt.entity.RequestEntity;
import com.javamaster.springsecurityjwt.repository.RequestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService  {

    @Autowired
    private RequestEntityRepository requestEntityRepository;

    public RequestEntity findByName(String name) {

        return requestEntityRepository.findByName(name);
    }
    /*public void findById(Integer id) {

         requestEntityRepository.updateById(id);
    }*/
    public void deleteById(Integer id) {

        requestEntityRepository.deleteById(id);
    }
    public RequestEntity saveRequest(RequestEntity reqEntity) {

        return requestEntityRepository.save(reqEntity);
    }
    public List<RequestEntity> getAll( ) {

        return requestEntityRepository.getAll();
    }
}
