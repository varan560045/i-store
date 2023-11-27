package com.dmdev.i.store.servlet;

import com.dmdev.i.store.dao.ClientDao;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/myServlet")
public class MyServlet extends HttpServlet {

    ClientDao clientDao = ClientDao.getInstance();
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        clientDao.findAll();
        resp.setContentType("text/html");
        try (PrintWriter writer = resp.getWriter()) {

            writer.write("Не могу разобраться, как вывести сюда список клиентов...:(((");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
