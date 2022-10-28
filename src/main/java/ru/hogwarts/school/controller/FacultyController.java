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
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
         facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Faculty> filterAgeFaculty(@RequestParam int age){
        Faculty foundFaculty = facultyService.filterAgeFaculty(age);
        if (foundFaculty == null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }
}
