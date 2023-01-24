package com.sda.student.service;

import com.sda.student.dao.Address;
import com.sda.student.dao.Student;
import com.sda.student.exception.StudentAlreadyExistsException;
import com.sda.student.exception.StudentException;
import com.sda.student.modal.AddressInfo;
import com.sda.student.modal.StudentInfo;
import com.sda.student.modal.StudentLookup;
import com.sda.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Student service class perform all student operations.
 */
@Service
@Slf4j
public class StudentService {

    private final StudentRepository repository;
    private final MongoTemplate mongoTemplate;

    public StudentService(StudentRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Save the student.
     *
     * @param studentInfo the student info
     * @return the student info
     */
    public StudentInfo saveStudent(StudentInfo studentInfo) {
        Student student = buildStudent(studentInfo);
        repository.findByEmail(student.getEmail()).ifPresentOrElse(student1 -> {
                    log.warn("Student already exists");
                    throw new StudentAlreadyExistsException("Student information already exists");
                }, () -> {
                    repository.save(student);
                }
        );

        return studentInfo;
    }

    /**
     * Builds the dao student object.
     *
     * @param studentInfo the student info
     * @return the instance of {@link Student}
     */
    private Student buildStudent(StudentInfo studentInfo) {
        Student student = new Student(studentInfo.getFirstName(), studentInfo.getLastName(),
                buildAddress(studentInfo.getAddressInfo()), studentInfo.getPhoneNumber(), studentInfo.getEmail(), studentInfo.getFavoriteSubjects());
        return student;

    }

    /**
     * Builds the dao address object.
     *
     * @param addressInfo the address info
     * @return the instance of {@link Address}
     */
    private Address buildAddress(AddressInfo addressInfo) {
        Address address = new Address(addressInfo.getLine1(), addressInfo.getLine2(),
               addressInfo.getCountry() ,addressInfo.getState(), addressInfo.getCity(),addressInfo.getPostalCode());
        return address;
    }

    /**
     * Builds the student info object.
     *
     * @param student the student
     * @return the instance of {@link StudentInfo}
     */
    private StudentInfo buildStudentInfo(Student student) {
        StudentInfo studentInfo = new StudentInfo(student.getFirstName(), student.getLastName(),
                buildAddressInfo(student.getAddress()), student.getPhoneNumber(), student.getEmail(), student.getFavoriteSubjects());
        return studentInfo;
    }

    /**
     * Builds the address info object.
     *
     * @param address the address
     * @return the instance of {@link AddressInfo}
     */
    private AddressInfo buildAddressInfo(Address address) {
        AddressInfo addressInfo = new AddressInfo(address.getLine1(), address.getLine2(),
                address.getCountry() ,address.getState(), address.getCity(),address.getPostalCode());
        return addressInfo;
    }

    /**
     * Find all students.
     *
     * @return list of all students
     */
    public List<StudentInfo> findAll() {
        List<Student> students = repository.findAll();

        return students.stream().map(student -> buildStudentInfo(student)).collect(Collectors.toList());
    }

    /**
     * Finds the student by email.
     *
     * @param email the email address
     * @return the StudentInfo
     */
    public StudentInfo findStudentByEmail(String email) {
        Student student = repository.findByEmail(email).get();
        return buildStudentInfo(student);
    }

    /**
     * Performs the student lookup.
     *
     * @param firstName the first name
     * @param lastName the last name
     * @param phoneNumber the phone number
     * @return the list of students matching criteria
     */
    public List<StudentInfo> studentLookup(String firstName, String lastName, String phoneNumber) {
        StudentLookup studentLookup = StudentLookup.newBuilder().withFirstName(firstName).withLastName(lastName).withPhoneNumber(phoneNumber).build();
        Map<String, String> queryMap = studentLookup.getQuery();
        if(queryMap.isEmpty()) {
            throw new StudentException("Atlest one query parameter required", StudentException.ExceptionType.INVALID_FIELD);
        }
        Criteria criteria = new Criteria();
        queryMap.entrySet().stream().forEach(stringStringEntry -> {
            criteria.and(stringStringEntry.getKey()).is(stringStringEntry.getValue());
        });

        Query query = new Query().addCriteria(criteria);

        List<Student> students = mongoTemplate.find(query, Student.class);
        return students.stream().map(student -> buildStudentInfo(student)).collect(Collectors.toList());
    }
}
