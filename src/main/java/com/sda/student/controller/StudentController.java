package com.sda.student.controller;

import com.sda.student.exception.StudentAlreadyExistsException;
import com.sda.student.exception.StudentException;
import com.sda.student.modal.StudentInfo;
import com.sda.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Student Rest API's
 */
@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    private StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * Save student information
     *
     * @param studentInfo the student info
     * @return {@instance ResponseEntity} containing status code
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<StudentInfo> saveStudent(@RequestBody StudentInfo studentInfo) {
        StudentInfo studentInfo1 = service.saveStudent(studentInfo);
        return new ResponseEntity<>(studentInfo1, HttpStatus.CREATED);
    }

    /**
     * Retrieves all student information.
     *
     * @return list of student information
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<StudentInfo>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    /**
     * Retrieves student by email.
     *
     * @param email the email address
     * @return student information
     */
    @GetMapping(path = "/{email}")
    @ResponseBody
    public ResponseEntity<StudentInfo> findStudentByEmail(@PathVariable("email") String email) {
        if (email == null || email.isBlank()) {
            throw new StudentException("Email address is null or blank", StudentException.ExceptionType.INVALID_FIELD);
        }
        return new ResponseEntity<>(service.findStudentByEmail(email), HttpStatus.OK);
    }

    /**
     * Performs student lookup.
     *
     * @param firstName   the first name
     * @param lastName    the last name
     * @param phoneNumber the phone number
     * @return list of student information matching criteria
     */
    @GetMapping(path = "/lookup")
    @ResponseBody
    public ResponseEntity<List<StudentInfo>> studentLookup(@RequestParam("firstName") String firstName,
                                                           @RequestParam("lastName") String lastName,
                                                           @RequestParam("phoneNumber") String phoneNumber) {
        return new ResponseEntity<>(service.studentLookup(firstName, lastName, phoneNumber), HttpStatus.OK);

    }

    @ExceptionHandler(value = StudentAlreadyExistsException.class)
    public ResponseEntity handleStudentAlreadyExistsException(StudentAlreadyExistsException studentAlreadyExistsException) {
        return new ResponseEntity("student already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = StudentException.class)
    public ResponseEntity handleStudentException(StudentException studentException) {
        if (StudentException.ExceptionType.INVALID_FIELD == studentException.getExceptionType()) {
            return new ResponseEntity("invalid or no input provided", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("unknown error occurred contact site admin at +1(888)999-1111", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
