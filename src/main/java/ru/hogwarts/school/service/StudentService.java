package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentService {
    private static final HashMap<Long, Student> studentMap =new HashMap<>();
    private long lastId = 1;

    public Student createStudent(Student student) {
        student.setId(lastId++);
        studentMap.put(lastId, student);
        return student;
    }

    public Collection<Student> findStudent() {
        return studentMap.values();
    }
    public static Student editStudent(Student student) {
        studentMap.put(student.getId(), student);
        return student;
    }
    public Student deleteStudent(long id) {
        return studentMap.remove(id);
    }

    public static Student filterAgeStudent(int age){

        return studentMap.get(age);
    }
}
