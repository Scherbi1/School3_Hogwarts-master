package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }
    public Collection<Student> findStudent() {
        return studentRepository.findAll();
    }
    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }
    public Student getStudentById(long id){
        return studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException());
    }
    public Collection<Student> findByAgeBetween(Integer min, Integer max) {
        return studentRepository.findByAgeBetween(min, max);
    }
}
    