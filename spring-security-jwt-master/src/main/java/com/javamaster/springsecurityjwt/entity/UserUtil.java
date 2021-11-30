package com.javamaster.springsecurityjwt.entity;

public class UserUtil {
    public static UserEntity createUser() {
        UserEntity user = new UserEntity();
        user.setLogin("mkozlovam7@gmail.com");
        user.setPassword("Darsmeved1");
        return user;
    }
}
