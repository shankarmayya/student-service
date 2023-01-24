package com.sda.student.controller.test;

import com.sda.student.controller.StudentController;
import com.sda.student.modal.AddressInfo;
import com.sda.student.modal.StudentInfo;
import com.sda.student.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;

    @Test
    public void testSaveStudentWhenValidStudentExpectSaveStudentInServiceInvoked() throws Exception {
        StudentInfo studentInfo = buildMockStudentInfo();
        when(service.saveStudent(any(StudentInfo.class))).thenReturn(studentInfo);
        this.mockMvc.perform(post("/api/v1/student").content(studentJsonContent()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().json(studentJsonContent()));

    }

    @Test
    public void testFindAllWhenValidStudentInfoAvailableExpectStudentInfoReturned() throws Exception {
        StudentInfo studentInfo = buildMockStudentInfo();
        when(service.findAll()).thenReturn(Arrays.asList(studentInfo));
        this.mockMvc.perform(get("/api/v1/student"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[" + studentJsonContent() + "]"));
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
     * @return the address
     */
    private AddressInfo buildAddressInfo() {
        AddressInfo addressInfo = new AddressInfo("202 Hartnell pl", "Apt 451", "USA", "CA", "Rancho Cordova",
                "95670");
        return addressInfo;
    }

    private String studentJsonContent() {
        String result = "{\n" +
                "            \"firstName\": \"Test\",\n" +
                "                \"lastName\": \"Student\",\n" +
                "                \"address\": {\n" +
                "            \"line1\": \"202 Hartnell pl\",\n" +
                "                    \"line2\": \"Apt 451\",\n" +
                "                    \"country\": \"USA\",\n" +
                "                    \"state\": \"CA\",\n" +
                "                    \"city\": \"Rancho Cordova\",\n" +
                "                    \"postalCode\": \"95670\"\n" +
                "        },\n" +
                "            \"phoneNumber\": \"9999999999\",\n" +
                "                \"email\": \"test.student@gmail.com\",\n" +
                "                \"favoriteSubjects\": [\n" +
                "            \"Maths\",\n" +
                "                    \"Science\"\n" +
                "    ]\n" +
                "        }";
        return result;

    }

}
