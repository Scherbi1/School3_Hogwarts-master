package ru.hogwarts.school.SqlRequestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.SqlRequestInterface.getStudentByAverageAge;
import ru.hogwarts.school.SqlRequestInterface.getStudentByIdDeskFive;
import ru.hogwarts.school.SqlRequestInterface.getStudentByName;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
public class SqlRequestController {
    private final StudentService studentService;

    public SqlRequestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/get-student-by-name")
    public List<getStudentByName> getStudentByNames() {
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
