package com.infoshareacademy.web;

import com.infoshareacademy.dao.ComputerDao;
import com.infoshareacademy.model.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.util.List;

@WebServlet(urlPatterns = "/computer")
public class ComputerServlet extends HttpServlet {

    private Logger LOG = LoggerFactory.getLogger(StudentServlet.class);

    @Inject
    private ComputerDao computerDao;

//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//
//        // Test data
//        // Students
//        computerDao.save(new Computer("Komputer 3", "WIN"));
//        computerDao.save(new Computer("Komputer 4", "LINUX"));
//        LOG.info("System time zone is: {}", ZoneId.systemDefault());
//    }

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
            addComputer(req, resp);
        } else if (action.equals("delete")) {
            deleteComputer(req, resp);
        } else if (action.equals("update")) {
            updateComputer(req, resp);
        } else {
            resp.getWriter().write("Unknown action.");
        }
    }

    private void updateComputer(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Updating Student with id = {}", id);


        final Computer existingComputer = computerDao.findById(id);
        if (existingComputer == null) {
            LOG.info("No Student found for id = {}, nothing to be updated", id);
        } else {
            existingComputer.setName(req.getParameter("name"));
            existingComputer.setOperatingSystem(req.getParameter("surname"));


            computerDao.update(existingComputer);
            LOG.info("Computer object updated: {}", existingComputer);
        }

        // Return all persisted objects
        findAll(req, resp);
    }

    private void addComputer(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        final Computer c = new Computer();
        c.setName(req.getParameter("name"));
        c.setOperatingSystem(req.getParameter("SystemOperacujny"));

        computerDao.save(c);
        LOG.info("Saved a new Computer object: {}", c);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void deleteComputer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Removing Computer with id = {}", id);

        computerDao.delete(id);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<Computer> result = computerDao.findAll();
        LOG.info("Found {} objects", result.size());
        for (Computer c : result) {
            resp.getWriter().write(c.toString() + "\n");
        }
    }
}
