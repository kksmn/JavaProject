package com.javamaster.springsecurityjwt.repository;

import com.javamaster.springsecurityjwt.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RequestEntityRepository extends JpaRepository<RequestEntity, Integer> {

    RequestEntity findByName(String name);

    @Transactional
    @Modifying
    @Query("update RequestEntity set status=true WHERE id = :id")
    void updateById(@Param("id") Integer id);

 /*   @Query("update RequestEntity set name=req.name, description=req.description WHERE id = :req.id")
    void updateById(@Param("Entity") RequestEntity req);*/
    void deleteById(Integer id);
    List<RequestEntity> findAll();
    Optional<RequestEntity> findById(Integer id);
}
