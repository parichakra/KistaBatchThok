package com.parichakra.batchthok.model;

import lombok.Data;

@Data
public class Student {
    private Long id;

    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private String email;
    private int english;
    private int science;
    private int math;

}
