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
import ru.hogwarts.school.service.StudentService;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;
  /* @AfterEach
    public void resetDb() {
        studentRepository.deleteAll();
    }*/

    @Test
    public void whenCreatePerson_thenStatus200() {

        Student student = new Student(122L,"Michail",24);

        ResponseEntity<Student> response = restTemplate.postForEntity("/student/create", student, Student.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody().getName(), is("Michail"));

        studentService.deleteStudent(response.getBody().getId());

    }

    @Test
    public void givenPerson_whenGetPerson_thenStatus200() {
        Student student = new Student(123L,"Joe", 22);
        Long id=studentService.createStudent(student).getId();
        Student person = restTemplate.getForObject("/student/{id}", Student.class, id);
        System.out.println("выа" + id);
        assertThat(person.getName(), is("Joe"));

        studentService.deleteStudent(id);

    }

   @Test
    public void whenUpdatePerson_thenStatus200() {
       Student student = new Student(123L,"Joe", 22);
       Long id=studentService.createStudent(student).getId();
       student = new Student(id, "Илья", 23);

        HttpEntity<Student> entity = new HttpEntity<Student>(student);

        ResponseEntity<Student> response = restTemplate.exchange("/student/edit", HttpMethod.PUT, entity, Student.class,
                id);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody().getName(), is("Илья"));

       studentService.deleteStudent(id);
    }

   @Test
    public void givenPerson_whenDeletePerson_thenStatus200() {
       Student student = new Student(123L, "Nick", 22);
       Long id = studentService.createStudent(student).getId();
      ResponseEntity<Student> response = restTemplate.exchange("/student/delete/{id}", HttpMethod.DELETE, null, Student.class,
           id);
      assertThat(response.getStatusCode(), is(HttpStatus.OK));
   }

    @Test
    public void givenPersons_whenGetPersons_thenStatus200() {
        Student student1 = new Student(123L, "Jane", 22);
        Student student2 = new Student(124L, "Илья", 25);
        Long id1 = studentService.createStudent(student1).getId();
        Long id2 = studentService.createStudent(student2).getId();
        student1.setId(id1);
        student2.setId(id2);
      try{  ResponseEntity<List<Student>> response = restTemplate.exchange("/student/get", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {
                });
       List<Student> persons = response.getBody();
       for (Student student:persons)
        System.out.println(student.getName());
       // assertThat(persons, contains(student1));
        assertThat(persons, contains(student1,student2));
      } finally {
          studentService.deleteStudent(id1);
          studentService.deleteStudent(id2);
      }

    }

}

