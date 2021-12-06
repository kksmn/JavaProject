package com.javamaster.springsecurityjwt.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "user_table")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String activationCode;

    @Column
    private String login;

    @Column
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 10)
    private String password;

    public String getActivationCode() {
        return activationCode;
    }
    public String getRoles(){ return roleEntity.getName();}

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;
}
