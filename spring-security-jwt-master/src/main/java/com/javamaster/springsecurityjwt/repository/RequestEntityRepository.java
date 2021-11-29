package com.javamaster.springsecurityjwt.repository;

import com.javamaster.springsecurityjwt.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequestEntityRepository extends JpaRepository<RequestEntity, Integer> {

    RequestEntity findByName(String name);

    @Transactional
    @Modifying
   /* @Query("update RequestEntity set user_id=user_id WHERE id = :id")
    void updateById(@Param("user_id") Integer user_id);*/

    /*@Query("update RequestEntity set status=true WHERE id = :id")
    void updateById(@Param("id") Integer id);*/

    void deleteById(Integer id);
    List<RequestEntity> findAllByOrderByIdAsc();

    @Query("select id,name from RequestEntity ")
    List<RequestEntity> getAll();
}
