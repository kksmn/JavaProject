package com.javamaster.springsecurityjwt.controller;

import com.javamaster.springsecurityjwt.config.jwt.JwtProvider;
import com.javamaster.springsecurityjwt.entity.UserEntity;
import com.javamaster.springsecurityjwt.exceptions.RequestException;
import com.javamaster.springsecurityjwt.exceptions.UserException;
import com.javamaster.springsecurityjwt.service.MailSender;
import com.javamaster.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.server.UID;
import java.util.UUID;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private MailSender mailSender;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) throws UserException {
        UserEntity u = new UserEntity();
        if(registrationRequest.getLogin()!=null) {
            if(registrationRequest.getPassword()!=null) {
                u.setPassword(registrationRequest.getPassword());
                u.setLogin(registrationRequest.getLogin());
                u.setActivationCode(UUID.randomUUID().toString());
                userService.saveUser(u);
                String message = String.format(
                        "Hello, %s! \n" +
                                "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                        u.getLogin(),
                        u.getActivationCode()
                );

                // mailSender.send(u.getLogin(),"Activation code",message);
                return "User is created";
            }
            else throw new UserException("Incorrect password");
        }
        else throw new UserException("Incorrect login");

    }

    @GetMapping("/activate/{code}")
    public String activate( @PathVariable String code) throws UserException {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            return "User is activated";
        } else {
            return "User hasn't been activated";
        }


    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) throws UserException {
        UserEntity userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getLogin());

        return new AuthResponse(token);
    }
}
