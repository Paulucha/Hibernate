package com.infoshareacademy.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.util.stream.Collectors.joining;

@Entity
@Table(name = "COURSE")
public class Course{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    public Course() {

    }


    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Course(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

    public Course(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Course{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", students=").append(students.stream().map(Student::getName).collect(joining(",")));
        sb.append('}');
        return sb.toString();
    }
}
