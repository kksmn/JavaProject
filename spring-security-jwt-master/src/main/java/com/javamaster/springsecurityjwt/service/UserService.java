package com.javamaster.springsecurityjwt.service;

import com.javamaster.springsecurityjwt.entity.RoleEntity;
import com.javamaster.springsecurityjwt.entity.UserEntity;
import com.javamaster.springsecurityjwt.exceptions.UserException;
import com.javamaster.springsecurityjwt.repository.RoleEntityRepository;
import com.javamaster.springsecurityjwt.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private RoleEntityRepository roleEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean saveUser(UserEntity userEntity) throws UserException {
        if(findByLogin(userEntity.getLogin())==null) {
            RoleEntity userRole = roleEntityRepository.findByName("ROLE_USER");
            userEntity.setRoleEntity(userRole);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userEntityRepository.save(userEntity);
            return true;
        }
        else throw new UserException("User with such login already exists");
    }

    public UserEntity findByLogin(String login) {
        return userEntityRepository.findByLogin(login);
    }

    public UserEntity findByLoginAndPassword(String login, String password) throws UserException {
        UserEntity userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        else throw new UserException("Incorrect login: such user doesn't exist");
        return null;
    }
    public boolean activateUser(String code) throws UserException {
        UserEntity userEntity=userEntityRepository.findByActivationCode(code);
        if (userEntity==null){
             throw new UserException("Incorrect login");
        }
        return true;
    }
}

