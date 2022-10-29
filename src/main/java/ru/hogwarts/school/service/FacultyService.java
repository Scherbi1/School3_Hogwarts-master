package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.Exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.HashMap;

@Service
public class FacultyService {
   private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Collection<Faculty> findFaculty() {
        return facultyRepository.findAll();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
          facultyRepository.deleteById(id);
    }

    public Faculty filterAgeFaculty(long age){
         return facultyRepository.findById(age).orElseThrow(()-> new FacultyNotFoundException());
    }
}
