package com.example.usermanagement.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.example.usermanagement.dao.UserDAO;
import com.example.usermanagement.model.User;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {

        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if ("delete".equals(action) && idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                userDAO.delete(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            response.sendRedirect("users");
            return;
        }
        List<User> liste = userDAO.listAll();
        request.setAttribute("users", liste);
        request.getRequestDispatcher("listUsers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        LocalDate dateNaissance = null;
        try {
            dateNaissance = LocalDate.parse(request.getParameter("dateNaissance"));

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            // gérer proprement (ex : message d'erreur, redirection)

        }

        User user = new User(0, name, email, phone, dateNaissance);
        userDAO.add(user);
        response.sendRedirect("users");

    }

}
