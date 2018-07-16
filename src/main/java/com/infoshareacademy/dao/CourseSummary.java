package com.infoshareacademy.dao;

import java.util.List;

public class CourseSummary {
    private String courseName;
    private List<String> courseAttendants;

    public CourseSummary(String courseName, List<String> courseAttendants) {
        this.courseName = courseName;
        this.courseAttendants = courseAttendants;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<String> getCourseAttendants() {
        return courseAttendants;
    }

    public void setCourseAttendants(List<String> courseAttendants) {
        this.courseAttendants = courseAttendants;
    }
}
