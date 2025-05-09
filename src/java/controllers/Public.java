/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.User;
import data.UserDA;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.catalina.realm.SecretKeyCredentialHandler;

/**
 *
 * @author kkmil
 */
public class Public extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = "/index.jsp";
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }
        
        HttpSession session = request.getSession();

        Logger LOG = Logger.getLogger(Public.class.getName());

        HashMap<String, String> errors = new HashMap();
        switch (action) {
            case "login": {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                User storedCreds = new User();

                try {
                    storedCreds = UserDA.getPasswordFromEmail(email);
                } catch (NamingException ex) {
                    LOG.log(Level.SEVERE, "*** Datasource lookup fail", ex);
                    errors.put("Error", "Problem with DB, try again later");
                    LOG.log(Level.SEVERE, "*** Datasource lookup fail", ex);
                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, "*** SQL problem", ex);
                    errors.put("Error", "Problem with DB, try again later");
                }

                SecretKeyCredentialHandler ch;
                try {
                    ch = new SecretKeyCredentialHandler();
                    ch.setAlgorithm("PBKDF2WithHmacSHA256");
                    ch.setKeyLength(256);
                    ch.setSaltLength(16);
                    ch.setIterations(4096);
                    request.setAttribute("password", password);
                    request.setAttribute("storedCreds", storedCreds);
                    if (storedCreds == null || !ch.matches(password, storedCreds.getPassword())) {
                        request.setAttribute("message", "invalid credentials");
                    } else {
                        User loggedInUser = UserDA.getUserFromEmail(email);
                        session.setAttribute("loggedInUser", loggedInUser);
                        url = "/userPack.jsp";
                    }
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, null, ex);
                    errors.put("Hash", "Error with hashing algorithm.");
                }
                break;
            }
            case "registration": {
                String message = "";
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                boolean isAdmin = false;
                int reportStrikes = 0;
                boolean activeStatus = true;
                String adminMessage = "";
                LocalDate createdDate = LocalDate.now();

                User user = new User();
                
                user.setUsername(username);
                
                if (user.getUsername() == null || user.getUsername().equals("")) {
                    message = "Must enter a username.";
                    errors.put("username", "Username Empty");
                }
                
                user.setEmail(email);
                
                if (user.getEmail() == null || user.getEmail().equals("")) {
                    message = "Must enter an email.";
                    errors.put("emailEmpty", "E-mail empty");
                }
                
                boolean emailExists = false;
                try {
                    emailExists = UserDA.emailExists(email);
                } catch (NamingException | SQLException ex) {
                    LOG.log(Level.SEVERE, "*** Datasource lookup fail", ex);
                    errors.put("Error", "Problem with DB, try again later");
                }
                
                if (emailExists) {
                    message = "Email already exists.";
                    errors.put("emailExist", "E-mail exists");
                }
                
                if (password == null || password.equals("")) {
                    message = "Must enter a password.";
                    errors.put("email", "Password Empty");
                }

                
                SecretKeyCredentialHandler ch;
                String hash = "";
                try {
                    ch = new SecretKeyCredentialHandler();
                    ch.setAlgorithm("PBKDF2WithHmacSHA256");
                    ch.setKeyLength(256);
                    ch.setSaltLength(16);
                    ch.setIterations(4096);
                    hash = ch.mutate(password);
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, null, ex);
                    errors.put("Hash", "Error with hashing algorithm.");
                }

                if (errors.isEmpty()) {
                    user.setPassword(hash);
                    user.setIsAdmin(isAdmin);
                    user.setReportStrikes(reportStrikes);
                    user.setActiveStatus(activeStatus);
                    user.setAdminMessage(adminMessage);
                    user.setCreatedDate(createdDate);
                    try {
                        UserDA.insertUser(user);
                    } catch (NamingException ex) {
                        LOG.log(Level.SEVERE, "*** Datasource lookup fail", ex);
                        errors.put("Error", "Problem with DB, try again later");
                    } catch (SQLException ex) {
                        LOG.log(Level.SEVERE, "*** SQL problem", ex);
                        errors.put("Error", "Problem with DB, try again later");
                    }
                    message = "Registration Successful";
                } else {
                    request.setAttribute("user", user);
                    message = "Registration Failed";
                    url = "/registration.jsp";
                }

                request.setAttribute("errors", errors);
                request.setAttribute("message", message);
                break;
            }
            case "logout": {
                request.getSession().invalidate();
                url = "/index.jsp";
                break;
            }
            case "goToRegistrationPage": {
                url = "/registration.jsp";
                break;
            }
            case "goToIndexPage": {
                url = "/index.jsp";
                break;
            }
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
