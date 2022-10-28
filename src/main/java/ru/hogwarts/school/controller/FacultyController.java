package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/Faculty")
public class FacultyController {
    private FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @GetMapping("/get")
    public ResponseEntity<Collection<Faculty>> getAllStudent() {
        return ResponseEntity.ok(facultyService.findFaculty());
    }

    @GetMapping("/create")
    public Faculty createFaculty(@RequestParam Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/edit")
    public  ResponseEntity<Faculty> editFaculty(@RequestParam Faculty faculty){
        Faculty foundFaculty = FacultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @GetMapping("/delete")
    public Faculty deleteFaculty(@PathVariable Long id) {
        return facultyService.deleteFaculty(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<Faculty> filterAgeFaculty(@RequestParam int age){
        Faculty foundFaculty = FacultyService.filterAgeFaculty(age);
        if (foundFaculty == null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }
}
