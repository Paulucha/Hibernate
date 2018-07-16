package com.infoshareacademy.web;

import com.infoshareacademy.dao.CourseDao;
import com.infoshareacademy.dao.CourseDao;
import com.infoshareacademy.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Transactional
@WebServlet(urlPatterns = "/courses")
public class CourseServlet extends HttpServlet{
   

        private Logger LOG = LoggerFactory.getLogger(com.infoshareacademy.web.CourseServlet.class);

        @Inject
        private CourseDao courseDao;


        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {

            final String action = req.getParameter("action");
            LOG.info("Requested action: {}", action);
            if (action == null || action.isEmpty()) {
                resp.getWriter().write("Empty action parameter.");
                return;
            }

            if (action.equals("findAll")) {
                findAll(req, resp);
            } else if (action.equals("add")) {
                addCourse(req, resp);
            } else if (action.equals("delete")) {
                deleteCourse(req, resp);
            } else if (action.equals("update")) {
                updateCourse(req, resp);
            } else {
                resp.getWriter().write("Unknown action.");
            }
        }

        private void updateCourse(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
            final Long id = Long.parseLong(req.getParameter("id"));
            LOG.info("Updating Course with id = {}", id);


            final Course existingCourse = courseDao.findById(id);
            if (existingCourse == null) {
                LOG.info("No Course found for id = {}, nothing to be updated", id);
            } else {
                existingCourse.setName(req.getParameter("name"));



                courseDao.update(existingCourse);
                LOG.info("Course object updated: {}", existingCourse);
            }

            // Return all persisted objects
            findAll(req, resp);
        }

        private void addCourse(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {

            final Course c = new Course();
            c.setName(req.getParameter("name"));



            courseDao.save(c);
            LOG.info("Saved a new Course object: {}", c);

            // Return all persisted objects
            findAll(req, resp);
        }

        private void deleteCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            final Long id = Long.parseLong(req.getParameter("id"));
            LOG.info("Removing Course with id = {}", id);

            courseDao.delete(id);

            // Return all persisted objects
            findAll(req, resp);
        }

        private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            final List<Course> result = courseDao.findAll();
            LOG.info("Found {} objects", result.size());
            for (Course a : result) {
                resp.getWriter().write(a.toString() + "\n");
            }
        }

    }
