package com.bloodbank.backend.dto;

import lombok.Data;

@Data
public class PersonDto {
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private String bloodType;
    private String email;
    private String address;
}
