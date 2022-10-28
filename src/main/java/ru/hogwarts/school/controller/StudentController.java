package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/Student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("/get")
    public ResponseEntity<Collection<Student>> getAllStudent() {
        return ResponseEntity.ok(studentService.findStudent());
    }

    @GetMapping("/create")
    public Student createStudent(@RequestParam Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/edit")
    public  ResponseEntity<Student> editStudent(@RequestParam Student student){
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/filter")
    public ResponseEntity<Student> filterAgeStudent(@RequestParam int age){
        Student foundStudent = studentService.filterAgeStudent(age);
        if (foundStudent == null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(foundStudent);
    }
}
