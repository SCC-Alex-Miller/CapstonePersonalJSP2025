/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.Account;
import business.Expense;
import business.Users;
import data.ExpenseDA;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import util.CSV;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kj0rd
 */
public class ExpenseController extends HttpServlet {
    
    private static final Logger LOG = Logger.getLogger(Expense.class.getName());

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        Users loggedInUser = (Users) request.getSession().getAttribute("loggedInUser");
        Account activeAccount = (Account) request.getSession().getAttribute("activeAccount");

        if (loggedInUser == null) {
            response.sendRedirect("Public");
            return;
        }

        LinkedHashMap<String, String> errors = new LinkedHashMap();

        String url = "/expense.jsp";
        String message = "";

        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        //get and set expenselist 
        LinkedHashMap<Integer, Expense> expenseList = new LinkedHashMap();

        int fkAccountID = activeAccount.getAccountID();

        try {
            expenseList = ExpenseDA.getExpenseList(fkAccountID);
        } catch (NamingException | SQLException e) {
            LOG.log(Level.SEVERE, url, e);
            message = "Problem getting expense list";
        }

        request.setAttribute("expenseList", expenseList);

        switch (action) {
            case "addExpense": {

                String name = request.getParameter("name");
                String amount = request.getParameter("amount");
                String date = request.getParameter("date");
                String category = request.getParameter("category");
                String frequency = request.getParameter("frequency");

                Expense expense = new Expense();

                //expense name
                expense.setExpenseName(name);

                if (expense.getExpenseName() == null || expense.getExpenseName().equals("")) {
                    errors.put("expenseName", "Expense Name empty");
                }
                //expense amount
                double parseAmount = -1;
                String pass = "";
                try {
                    parseAmount = Float.parseFloat(amount);
                    pass = "yes";

                } catch (NumberFormatException e) {
                    LOG.log(Level.SEVERE, url, e);
                    errors.put("expenseAmount", "Expense amount must be real number");
                    pass = "no";
                }
                if (pass.equals("yes")) {
                    expense.setAmount(parseAmount);
                }

                //transaction date
                if (date == null || date.equals("")) {
                    errors.put("expenseDate", "Need to select a date");
                } else {
                    try {
                        LocalDate localDate = LocalDate.parse(date);
                        expense.setTransactionDate(localDate);

                    } catch (Exception e) {
                        LOG.log(Level.SEVERE, url, e);
                        errors.put("expenseDate", "Error parsing Date");
                    }
                }
                //category is seeded to auto send 1 for now until we get drop down list worked out
                int catNumber = -1;
                String passCat = "";
                try {
                    catNumber = Integer.parseInt(category);
                    passCat = "yes";
                } catch (NumberFormatException e) {
                    LOG.log(Level.SEVERE, url, e);
                    errors.put("expenseCategory", "Must select a category");
                    
                }
                if (passCat.equals("yes")) {
                    expense.setFkCategoryID(catNumber);
                }

                //frequency
                int parseFreq = -1;
                String passInt = "";

                try {
                    parseFreq = Integer.parseInt(frequency);
                    passInt = "yes";
                } catch (NumberFormatException e) {
                    LOG.log(Level.SEVERE, url, e);
                    errors.put("expenseFrequency", "Must be a whole number");
                    passInt = "no";
                }

                if (passInt.equals("yes")) {
                    expense.setFrequency(parseFreq);
                }

                //add fkAccountID to expense
                expense.setFkAccountID(fkAccountID);

                //Inserting expense to database
                if (errors.isEmpty()) {
                    try {
                        ExpenseDA.insert(expense);
                    } catch (NamingException | SQLException e) {
                        LOG.log(Level.SEVERE, url, e);
                        message = "Problem with inserting expense";
                    }

                }

                //Reset expenseList to dynamically load
                try {
                    expenseList = ExpenseDA.getExpenseList(fkAccountID);
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Problem getting expense list for dynamic load";
                }

                request.setAttribute("expenseList", expenseList);
                request.setAttribute("errors", errors);

                break;
            }
            case "update": {

                break;
            }
            case "delete": {

                break;
            }
            case "export": {
                
                String fileName = request.getParameter("exportName");
                
                String filePath = System.getProperty("user.home") + "/Documents/" + fileName;

                
                if(fileName == null || fileName.equals("")){
        
                    errors.put("exportName", "File Name Required");                    
                }
                
                 try {
                    expenseList = ExpenseDA.getExpenseList(fkAccountID);
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Problem getting expense list for export";
                }
                
                if(errors.isEmpty()){
                    
                    try{
                        CSV.export(expenseList, filePath);
                    }catch(IOException e){
                        LOG.log(Level.SEVERE, url, e);
                        message = "Problem sending expense list to csv";
                    }
                }
                
                break;
            }

        }
        getServletContext()
                .getRequestDispatcher(url).forward(request, response);

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
