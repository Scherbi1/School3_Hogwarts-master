package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.SqlRequestInterface.getStudentByAverageAge;
import ru.hogwarts.school.SqlRequestInterface.getStudentByIdDeskFive;

import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    private static final Logger LOG = LoggerFactory.getLogger(StudentService.class );

    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;

        this.avatarRepository = avatarRepository;

    }

    public Student createStudent(Student student) {
        LOG.debug("Method createStudent was invoked");
        return studentRepository.save(student);
    }
    public Collection<Student> findStudent() {
        LOG.debug("Method findStudent was invoked");
        return studentRepository.findAll();
    }
    public Student editStudent(Student student) {
        LOG.debug("Method editStudent was invoked");
        return studentRepository.save(student);
    }
    public void deleteStudent(long id) {
        LOG.debug("Method deleteStudent was invoked");
            studentRepository.deleteById(id);
        }

    public Student getStudentById(long id){
        LOG.debug("Method getStudentById was invoked");
        return studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException());
    }
    public Collection<Student> findByAgeBetween(Integer min, Integer max) {
        LOG.debug("Method findByAgeBetween was invoked");
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Integer> getStudentByNames() {
        LOG.debug("Method getStudentByNames was invoked");
        return studentRepository.getStudentByName();
    }

    public List<getStudentByAverageAge> getStudentByAverageAges() {
        LOG.debug("Method getStudentByAverageAges was invoked");
        return studentRepository.getStudentByAverageAge();
    }
    public List<getStudentByIdDeskFive> getStudentByIdDeskFives(){
        LOG.debug("Method getStudentByIdDeskFives was invoked");
        return studentRepository.getStudentByIdDeskFive();
    }

    public Stream<String> findStudentsNamedStartedWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s ->s.startsWith("A"))
                .sorted();

    }

    public double findAvarageAgeAllStudent() {

        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElseThrow(StudentNotFoundException::new);
    }


}
    