package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }
    @GetMapping("/get")
    public ResponseEntity<Collection<Faculty>> getAllStudent() {
        return ResponseEntity.ok(facultyService.findFaculty());
    }

    @PostMapping("/create")
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping("/edit")
    public  ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty){
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
         facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id){
        Faculty foundFaculty = facultyService.getFacultyById(id);
        if (foundFaculty == null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }
    @GetMapping("/name-and-color")
    public ResponseEntity<Collection<Faculty>> findFacultyByNameAndColor(@RequestParam String title, @RequestParam String color) {
        return ResponseEntity.ok(facultyService.findFacultyByNameIgnoreCaseAndColorIgnoreCase(title, color));
    }
}
