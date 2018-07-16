package com.infoshareacademy.dao;

import com.infoshareacademy.model.Course;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Stateless
public class CourseDao {


    @PersistenceContext
    private EntityManager entityManager;

    public Long save(Course c) {
        entityManager.persist(c);
        return c.getId();
    }

    public Course update(Course c) {
        return entityManager.merge(c);
    }

    public void delete(Long id) {
        final Course c = entityManager.find(Course.class, id);
        if (c != null) {
            entityManager.remove(c);
        }
    }

    public  Course findById(Long id) {
        return entityManager.find(Course.class, id);
    }

    public List<Course> findAll() {
        final Query query = entityManager.createQuery("SELECT c FROM Course c");

        return query.getResultList();
    }
//    public List<CourseSummary> getCoursesDetails(){
//
//        List<Course> courses = findAll();
//        return  courses.stream().map(c -> {
//            List<String> attenders = c.getStudents()
//            CourseSummary Summary = new CourseSummary(c.getName())
//        })
//    }
}


