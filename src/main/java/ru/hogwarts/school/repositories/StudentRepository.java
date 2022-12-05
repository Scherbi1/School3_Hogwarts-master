package ru.hogwarts.school.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.SqlRequestInterface.getStudentByAverageAge;
import ru.hogwarts.school.SqlRequestInterface.getStudentByIdDeskFive;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(Integer min, Integer max);

    long findByAvatarId(Long id);

@Query(value = "SELECT COUNT(*)  FROM student", nativeQuery = true)
    List<Integer> getStudentByName();
@Query(value = "SELECT AVG(age)  FROM student", nativeQuery = true)
    List<getStudentByAverageAge> getStudentByAverageAge();
@Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5" , nativeQuery = true)
    List<getStudentByIdDeskFive> getStudentByIdDeskFive();
}
