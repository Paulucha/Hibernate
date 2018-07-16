package com.infoshareacademy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static java.util.stream.Collectors.joining;

@Entity
@Table(name = "ADRESS")
public class Adress {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    @NotNull
    private String street;


    @Column(name = "city")
    @NotNull
    private String city;

    @OneToMany(mappedBy = "adress", fetch = FetchType.LAZY)
    private Set<Student> students;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Adress(String street, String city) {
        this.street = street;
        this.city = city;
    }


    public Adress(String street, String city, Set<Student> students) {
        this.street = street;
        this.city = city;
        this.students = students;
    }

    public Adress() {
    }

    @Override
    public String toString() {
        return "Adress{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", students=" + students.stream().map(Student::getName).collect(joining(",")) +
                '}';
    }
}
