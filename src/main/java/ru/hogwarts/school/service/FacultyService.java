package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;

@Service
public class FacultyService {
    private static final HashMap<Long, Faculty> facultyMap =new HashMap<>();
    private long lastId = 1;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(lastId++);
        facultyMap.put(lastId, faculty);
        return faculty;
    }

    public Collection<Faculty> findFaculty() {
        return facultyMap.values();
    }

    public static Faculty editFaculty(Faculty faculty) {
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(long id) {
        return facultyMap.remove(id);
    }

    public static Faculty filterAgeFaculty(int age){
        return facultyMap.get(age);
    }
}
