package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.SqlRequestInterface.getStudentByAverageAge;
import ru.hogwarts.school.SqlRequestInterface.getStudentByIdDeskFive;

import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;



    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;

        this.avatarRepository = avatarRepository;

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
          // long avatarId;
       // avatarId= studentRepository.findByAvatarId(id);
         //   avatarRepository.deleteById(avatarId);
            studentRepository.deleteById(id);
       /* student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student is not found"));
        avatarRepository.deleteByStudent(student);
        studentRepository.deleteById(id);*/
        }

    public Student getStudentById(long id){
        return studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException());
    }
    public Collection<Student> findByAgeBetween(Integer min, Integer max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Integer> getStudentByNames() {
        return studentRepository.getStudentByName();
    }

    public List<getStudentByAverageAge> getStudentByAverageAges() {
        return studentRepository.getStudentByAverageAge();
    }
    public List<getStudentByIdDeskFive> getStudentByIdDeskFives(){
        return studentRepository.getStudentByIdDeskFive();
    }
}
    