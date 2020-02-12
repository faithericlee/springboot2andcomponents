package com.cs.springboot2andcomponents.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class User {
    private Long id;
    private String name;
    private Integer age;

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Email
    private String email;

}