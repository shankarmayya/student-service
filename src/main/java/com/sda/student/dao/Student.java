package com.sda.student.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;
/**
 * Holds the student information.
 */
@Data
public class Student {
    @Id
    private String id;
    private final String firstName;
    private final String lastName;
    private final Address address;
    private final String phoneNumber;
    private final String email;
    private final List<String> favoriteSubjects;
}
