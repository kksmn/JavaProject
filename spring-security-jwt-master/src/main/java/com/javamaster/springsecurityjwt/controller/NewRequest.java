package com.javamaster.springsecurityjwt.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class NewRequest {
    private Integer id;
    private String name;
    private String description;
    private Boolean status;


}
