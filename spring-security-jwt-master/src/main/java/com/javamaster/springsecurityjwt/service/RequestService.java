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
    public void approveById(Integer id) {

         requestEntityRepository.updateById(id);
    }
    public void deleteById(Integer id) {

        requestEntityRepository.deleteById(id);
    }
    public boolean saveRequest(RequestEntity reqEntity) {

         requestEntityRepository.save(reqEntity);
         return true;
    }
    public List<RequestEntity> getAll( ) {

        return requestEntityRepository.findAll();
    }
    public Optional<RequestEntity> findById(Integer id ) {

        return requestEntityRepository.findById(id);
    }
}
