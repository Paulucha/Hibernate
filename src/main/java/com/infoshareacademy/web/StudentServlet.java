package com.infoshareacademy.web;

import com.infoshareacademy.dao.AdressDao;
import com.infoshareacademy.dao.ComputerDao;
import com.infoshareacademy.dao.CourseDao;
import com.infoshareacademy.dao.StudentDao;
import com.infoshareacademy.model.Adress;
import com.infoshareacademy.model.Computer;
import com.infoshareacademy.model.Course;
import com.infoshareacademy.model.Student;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/student")
public class StudentServlet extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(StudentServlet.class);
    @Inject
    private ComputerDao computerDao;
    @Inject
    private StudentDao studentDao;
    @Inject
    private AdressDao adressDao;
@Inject
private CourseDao courseDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);


        // Courses
        Course kurs1 = new Course("JAVA");
        courseDao.save(kurs1);
        Course kurs2 = new Course("FOTO");
        Course kurs3 = new Course("TANIEC");
        courseDao.save(kurs2);
        courseDao.save(kurs3);

        // Computers
        computerDao.save(new Computer("Komputer 3", "WIN"));
        computerDao.save(new Computer("Komputer 4", "LINUX"));

        // Adressses
        Adress a3 = new Adress("sfgrg", "gdfg");
        adressDao.save(new Adress("Ganska", "Kartuzy"));
        Adress a2 = new Adress("Kartuska", "Gdansk");
        adressDao.save(a2);
        adressDao.save(a3);

        // Students
        studentDao.save(new Student("Michal", "nazwiskomiachala", LocalDate.of(1991, 05, 6), null, a3, Collections.singletonList(kurs1)));
        studentDao.save(new Student("Marek", "nazwoskoMarrka", LocalDate.of(1695, 05, 9), null, a2, Collections.singletonList(kurs2)));
        studentDao.save(new Student("Ania", "Kowalska", LocalDate.of(1995, 06, 12), null, a2, Collections.singletonList(kurs1)));

        LOG.info("System time zone is: {}", ZoneId.systemDefault());
    }

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
            addStudent(req, resp);
        } else if (action.equals("delete")) {
            deleteStudent(req, resp);
        } else if (action.equals("update")) {
            updateStudent(req, resp);
        } else {
            resp.getWriter().write("Unknown action.");
        }
    }

    private void updateStudent(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Updating Student with id = {}", id);

//        final String surname = new String("surname");
//        LOG.info("Updating Student with surmane = {}", surname);

        final Student existingStudent = studentDao.findById(id);
        if (existingStudent == null) {
            LOG.info("No Student found for id = {}, nothing to be updated", id);
        } else {
            existingStudent.setName(req.getParameter("name"));
            existingStudent.setSurname(req.getParameter("surname"));
            existingStudent.setDateOfBirth(LocalDate.parse(req.getParameter("date of birth")));
            existingStudent.setComputer(computerDao.findById(Long.parseLong(req.getParameter("idkomp"))));

            studentDao.update(existingStudent);
            LOG.info("Student object updated: {}", existingStudent);
        }

        // Return all persisted objects
        findAll(req, resp);
    }

    private void addStudent(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String computerIdStr = req.getParameter("computerId");
        Long computerId = Long.valueOf(computerIdStr);
        final Computer c = computerDao.findById(computerId);

        String addressIdStr = req.getParameter("addressId");
        Long addressId = Long.valueOf(addressIdStr);
        final Adress a = adressDao.findById(addressId);


        final Student p = new Student();
        p.setName(req.getParameter("name"));
        p.setSurname(req.getParameter("surname"));
        p.setDateOfBirth(LocalDate.parse(req.getParameter("date")));
        p.setComputer(computerDao.findById(Long.parseLong(req.getParameter("idkomp"))));
        p.setAdress(a);
        studentDao.save(p);
        LOG.info("Saved a new Student object: {}", p);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Removing Student with id = {}", id);

        studentDao.delete(id);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<Student> result = studentDao.findAll();
        LOG.info("Found {} objects", result.size());
        for (Student p : result) {
            resp.getWriter().write(p.toString() + "\n");
        }
    }
}
