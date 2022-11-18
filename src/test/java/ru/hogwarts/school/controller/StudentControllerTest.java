package ru.hogwarts.school.controller;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;


import static org.hamcrest.CoreMatchers.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

public class StudentControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    public void resetDb() {
        studentRepository.deleteAll();
    }

    @Test
    public void whenCreatePerson_thenStatus200() {

        Student student = new Student();
        long id = createTestPerson(121L,"Michail", 22).getId();
        ResponseEntity<Student> response = restTemplate.postForEntity("/student/create", student, Student.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody().getName(), is("Michail"));
    }

    @Test
    public void givenPerson_whenGetPerson_thenStatus200() {

        long id = createTestPerson(122L,"Joe", 22).getId();

        Student person = restTemplate.getForObject("/student/{id}", Student.class, id);
        assertThat(person.getName(), is("Joe"));
    }

    @Test
    public void whenUpdatePerson_thenStatus200() {

        long id = createTestPerson(123L,"Nick", 33).getId();
        Student student1 = new Student();
        HttpEntity<Student> entity = new HttpEntity<Student>(student1);

        ResponseEntity<Student> response = restTemplate.exchange("/student/edit", HttpMethod.PUT, entity, Student.class,
                id);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody().getName(), is("Michail"));
    }

    @Test
    public void givenPerson_whenDeletePerson_thenStatus200() {

        long id = createTestPerson(124L,"Nick",33).getId();
        ResponseEntity<Student> response = restTemplate.exchange("/student/{id}", HttpMethod.DELETE, null, Student.class,
                id);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), is(id));
        assertThat(response.getBody().getName(), is("Nick"));

    }

    @Test
    public void givenPersons_whenGetPersons_thenStatus200() {
        createTestPerson(122L,"Joe", 22);
        createTestPerson(125L,"Jane",27);
        ResponseEntity<List<Student>> response = restTemplate.exchange("/student/get", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        List<Student> persons = response.getBody();
        assertThat(persons, hasSize(2));
        assertThat(persons.get(1).getName(), is("Jane"));
    }

    private Student createTestPerson(Long id,String name, int age) {
        Student emp = new Student();
        return studentRepository.save(emp);
    }

}
