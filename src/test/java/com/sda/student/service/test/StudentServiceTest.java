package com.sda.student.service.test;

import com.sda.student.dao.Address;
import com.sda.student.dao.Student;
import com.sda.student.exception.StudentAlreadyExistsException;
import com.sda.student.modal.AddressInfo;
import com.sda.student.modal.StudentInfo;
import com.sda.student.repository.StudentRepository;
import com.sda.student.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService classUnderTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveStudentWhenStudentInfoExistsExpectException() {
        Optional<Student> optionalStudent = Optional.of(buildMockStudent());
        when(studentRepository.findByEmail(anyString())).thenReturn(optionalStudent);


        StudentAlreadyExistsException thrown = Assertions.assertThrows(StudentAlreadyExistsException.class, () -> {
            classUnderTest.saveStudent(buildMockStudentInfo());
        }, "StudentAlreadyExistsException was expected");

        Assertions.assertEquals("Student information already exists", thrown.getMessage());
    }

    @Test
    public void testSaveStudentWhenValidStudentInfoGivenExpectStudentInfoSaved() {
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(buildMockStudent());
        StudentInfo studentInfo = classUnderTest.saveStudent(buildMockStudentInfo());
        assertNotNull(studentInfo);

    }

    /**
     * Builds the mock student info.
     *
     * @return the student info
     */
    private StudentInfo buildMockStudentInfo() {
        StudentInfo studentInfo = new StudentInfo("Test", "Student", buildAddressInfo(), "9999999999",
                "test.student@gmail.com", Arrays.asList("Maths", "Science"));
        return studentInfo;
    }

    /**
     * Builds the mock address info.
     *
     * @return the address info
     */
    private AddressInfo buildAddressInfo() {
        AddressInfo addressInfo = new AddressInfo("202 Hartnell pl", "Apt 451", "USA", "CA", "Rancho Cordova",
                "95670");
        return addressInfo;
    }

    /**
     * Builds the mock dao student.
     *
     * @return the dao student
     */
    private Student buildMockStudent() {
        Student student = new Student("Test", "Student", buildAddress(), "9999999999",
                "test.student@gmail.com", Arrays.asList("Maths", "Science"));
        return student;
    }

    /**
     * Builds the mock dao address.
     *
     * @return the dao address
     */
    private Address buildAddress() {
        Address address = new Address("202 Hartnell pl", "Apt 451", "USA", "CA", "Rancho Cordova",
                "95670");
        return address;
    }

}
