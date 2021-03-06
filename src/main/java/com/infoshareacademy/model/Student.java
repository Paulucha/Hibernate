package com.infoshareacademy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "STUDENTS")

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;


    @Column(name = "surname")
    @NotNull
    private String surname;

    @Column(name = "date")
    @NotNull
    private LocalDate dateOfBirth;

    @OneToOne
    @JoinColumn(name = "computer_id", unique = true)
    private Computer computer;


    @ManyToOne
    @JoinColumn(name = "adress_id")
    private Adress adress;

    @ManyToMany
    @JoinTable(name = "STUDENTS_TO_COURSES",
            joinColumns = @JoinColumn(name = "student_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_ID", referencedColumnName = "id"))
    private List<Course> courses;

    public Student(String name, String surname, LocalDate dateOfBirth, Computer computer, Adress adress, List<Course> courses) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.computer = computer;
        this.adress = adress;
        this.courses = courses;
    }

    public Student(String name, String surname, LocalDate dateOfBirth, Computer computer, Adress adress) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.computer = computer;
        this.adress = adress;
    }

    public Student(String name, String surname, LocalDate dateOfBirth, Computer computer) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.computer = computer;
    }

    public Student() {

    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public Student(String michal, String nazwiskomiachala, LocalDate of, Object o, Adress a3, List<Course> kurs1, Course kurs2) {

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Student(String name, String surname, LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", computer=" + computer +
                ", adress=" + adress.getStreet() + " " + adress.getCity() +

                '}';
    }
}

