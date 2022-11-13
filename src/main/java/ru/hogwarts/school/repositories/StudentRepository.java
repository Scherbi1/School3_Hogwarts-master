package ru.hogwarts.school.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.SqlRequestInterface.getStudentByAverageAge;
import ru.hogwarts.school.SqlRequestInterface.getStudentByIdDeskFive;
import ru.hogwarts.school.SqlRequestInterface.getStudentByName;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(Integer min, Integer max);

    long findByAvatarId(Long id);

@Query(value = "SELECT name, SUM(name) as sumStudent FROM student GROUP BY name", nativeQuery = true)
    List<getStudentByName> getStudentByName();
@Query(value = "SELECT AVG(age) as AvgAge FROM student GROUP BY age", nativeQuery = true)
    List<getStudentByAverageAge> getStudentByAverageAge();
@Query(value = "SELECT TOP (5) id as id, name as name, age as age FROM student ORDER BY id DESC" , nativeQuery = true)
    List<getStudentByIdDeskFive> getStudentByIdDeskFive();
}
