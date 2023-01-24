package com.sda.student.modal;

import com.sda.student.dao.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Models the student information.
 */
@Data
@AllArgsConstructor
public class StudentInfo {

    private final String firstName;
    private final String lastName;
    private final AddressInfo addressInfo;
    private final String phoneNumber;
    private final String email;
    private final List<String> favoriteSubjects;
}
