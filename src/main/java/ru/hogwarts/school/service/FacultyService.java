package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;

@Service
public class FacultyService {
   private final FacultyRepository facultyRepository;

   private static final Logger LOG = LoggerFactory.getLogger(FacultyService.class );
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        LOG.debug("Method createFaculty was invoked");
        return facultyRepository.save(faculty);
    }

    public Collection<Faculty> findFaculty() {
        LOG.debug("Method findFaculty was invoked");
        return facultyRepository.findAll();
    }

    public Faculty editFaculty(Faculty faculty) {
        LOG.debug("Method editFaculty was invoked");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        LOG.debug("Method deleteFaculty was invoked");
          facultyRepository.deleteById(id);
    }

    public Faculty getFacultyById(long id){
        LOG.debug("Method getFacultyById was invoked");
         return facultyRepository.findById(id).orElseThrow(()-> new FacultyNotFoundException());
    }

    public Collection<Faculty> findFacultyByNameIgnoreCaseAndColorIgnoreCase(String title, String color) {
        LOG.debug("Method findFacultyByNameIgnoreCaseAndColorIgnoreCase was invoked");
        return facultyRepository.findFacultyByNameIgnoreCaseAndColorIgnoreCase(title, color);
    }

    public String findLondestNameFaculty() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(FacultyNotFoundException::new);
    }
}
