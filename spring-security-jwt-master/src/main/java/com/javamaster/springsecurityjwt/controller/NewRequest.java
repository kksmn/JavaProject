package com.javamaster.springsecurityjwt.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class NewRequest {
//{"name":"request4", "description":"desc", "remuneration":56.5}
    private Integer id;
    /*@NotEmpty*/
    private String name;
    private String description;
    private Boolean status;


}
