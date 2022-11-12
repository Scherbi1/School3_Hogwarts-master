package ru.hogwarts.school.controller;

import com.github.javafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;


import java.util.List;
import java.util.stream.Stream;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.in;

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

    private final Faker faker = new Faker();
    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    public void createTest() {
        addStudent(generateStudent(addFaculty(generateFaculty())));
    }

    public Student addStudent(Student student) {
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/student/create", student, Student.class);
        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentResponseEntity.getBody()).isNotNull();
        assertThat(studentResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(student);
        assertThat(studentResponseEntity.getBody().getId()).isNotNull();
        return studentResponseEntity.getBody();
    }


    public Faculty addFaculty(Faculty faculty) {
        ResponseEntity<Faculty> facultyResponseEntity= testRestTemplate.postForEntity("http://localhost:" + port + "/faculty/create", faculty, Faculty.class);
        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(facultyResponseEntity.getBody()).isNotNull();
        assertThat(facultyResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(faculty);
        assertThat(facultyResponseEntity.getBody().getId()).isNotNull();
        return facultyResponseEntity.getBody();
    }

    @Test
    public void putTest() {
        Faculty faculty1 = addFaculty(generateFaculty());
        Faculty faculty2 = addFaculty(generateFaculty());
        Student student = addStudent(generateStudent(faculty1));

        ResponseEntity<Student> getForEntityResponse = testRestTemplate.postForEntity("http://localhost:" + port + "/student/edit", student.getId(), Student.class);
        assertThat(getForEntityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getForEntityResponse.getBody()).isNotNull();
        assertThat(getForEntityResponse.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(student);
        assertThat(getForEntityResponse.getBody().getId()).isNotNull();

        student.setFaculty(faculty2);
        ResponseEntity<Student> recordResponseEntity =  testRestTemplate.postForEntity("http://localhost:" + port + "/student/edit", student.getId(), Student.class);
        assertThat(recordResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(recordResponseEntity.getBody()).isNotNull();
        assertThat(recordResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(student);
        assertThat(recordResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    public void findByAgeBetweenTest() {
        List<Faculty> faculty = Stream.generate(this::generateFaculty)
                .limit(5)
                .map(this::addFaculty)
                .toList();
        List<Student> students = Stream.generate(() ->generateStudent(faculty.get(faker.random().nextInt(faculty.size()))))
                .limit(50)
                .map(this::addStudent)
                .toList();
        int minAge=14;
        int maxAge=21;
        List<Student> expectedStudents = students.stream()
                .filter(student -> student.getAge()>=minAge && student.getAge()<=maxAge)
                .toList();
        ResponseEntity<List<Student>> getForEntityResponse = testRestTemplate.exchange(
                "http://localhost:" + port + "/between",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                }, minAge,maxAge
        );
        assertThat(getForEntityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getForEntityResponse.getBody())
                .hasSize(expectedStudents.size())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedStudents);
    }
    private Student generateStudent(Faculty faculty) {
        Student student = new Student();
        student.setName(faker.harryPotter().character());
        student.setAge(faker.random().nextInt(10,30));
        if (faculty != null) {
            student.setFaculty(faculty);
        }
        return student;
    }
    private Faculty generateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName(faker.harryPotter().house());
        faculty.setColor(faker.color().name());
        return faculty;
    }
}
