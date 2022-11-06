package ru.hogwarts.school.controller;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;


    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    private Student addStudent(Student student) {
        ResponseEntity<Student> student1 = testRestTemplate.postForEntity("http://localhost:" + port + "/student", student, Student);
        assertThat(student1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(student1.getBody()).isNotNull();
        assertThat(student1.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(student);
        assertThat(student1.getBody().getId()).isNotNull();

        return student1.getBody();
    }
}
