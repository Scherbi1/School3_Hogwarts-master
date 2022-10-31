package ru.hogwarts.school.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
@Entity
public class Faculty {
    @Id
    @GeneratedValue
    private long id;

    private String title;
    private String color;
    @OneToMany(mappedBy = "position")
    private Collection<Student> student;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id && Objects.equals(title, faculty.title) && Objects.equals(color, faculty.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, color);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
