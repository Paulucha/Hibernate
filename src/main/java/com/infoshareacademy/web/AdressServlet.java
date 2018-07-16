package com.infoshareacademy.web;


import com.infoshareacademy.dao.AdressDao;
import com.infoshareacademy.dao.ComputerDao;
import com.infoshareacademy.model.Adress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@Transactional
@WebServlet(urlPatterns = "/adress")
public class AdressServlet extends HttpServlet {


    private Logger LOG = LoggerFactory.getLogger(AdressServlet.class);

    @Inject
    private AdressDao adressDao;


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
            addAdress(req, resp);
        } else if (action.equals("delete")) {
            deleteAdress(req, resp);
        } else if (action.equals("update")) {
            updateAdress(req, resp);
        } else {
            resp.getWriter().write("Unknown action.");
        }
    }

    private void updateAdress(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Updating Adress with id = {}", id);


        final Adress existingAdress = adressDao.findById(id);
        if (existingAdress == null) {
            LOG.info("No Adress found for id = {}, nothing to be updated", id);
        } else {
            existingAdress.setStreet(req.getParameter("street"));
            existingAdress.setCity(req.getParameter("city"));


            adressDao.update(existingAdress);
            LOG.info("Adress object updated: {}", existingAdress);
        }

        // Return all persisted objects
        findAll(req, resp);
    }

    private void addAdress(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        final Adress a = new Adress();
        a.setStreet(req.getParameter("street"));
        a.setCity(req.getParameter("city"));


        adressDao.save(a);
        LOG.info("Saved a new Adress object: {}", a);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void deleteAdress(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final Long id = Long.parseLong(req.getParameter("id"));
        LOG.info("Removing Adress with id = {}", id);

        adressDao.delete(id);

        // Return all persisted objects
        findAll(req, resp);
    }

    private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<Adress> result = adressDao.findAll();
        LOG.info("Found {} objects", result.size());
        for (Adress a : result) {
            resp.getWriter().write(a.toString() + "\n");
        }
    }
}
