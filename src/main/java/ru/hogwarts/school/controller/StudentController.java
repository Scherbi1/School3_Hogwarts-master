package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.SqlRequestInterface.getStudentByAverageAge;
import ru.hogwarts.school.SqlRequestInterface.getStudentByIdDeskFive;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;
    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }
    @GetMapping("/get")
    public ResponseEntity<Collection<Student>> getAllStudent() {
        return ResponseEntity.ok(studentService.findStudent());
    }

    @PostMapping("/create")
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/edit")
    public  ResponseEntity<Student> editStudent(@RequestBody Student student){
        Student foundStudent = studentService.editStudent(student);
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        Student foundStudent = studentService.getStudentById(id);
        return ResponseEntity.ok(foundStudent);
    }

    @GetMapping("/between")
    public ResponseEntity<Collection<Student>> FindAgeBetween(@RequestParam Integer min, @RequestParam Integer max) {
        return ResponseEntity.ok(studentService.findByAgeBetween(min,max));
    }
    @GetMapping("/get-student-by-name")
    public List<Integer> getStudentByNames() {
        return studentService.getStudentByNames();
    }
    @GetMapping("/get-student-by-average-age")
    public List<getStudentByAverageAge> getStudentByAverageAges() {
        return studentService.getStudentByAverageAges();
    }
    @GetMapping("/get-student-by-id-desc-five")
    public List<getStudentByIdDeskFive> getStudentByIdDeskFives() {
        return studentService.getStudentByIdDeskFives();
    }
}
