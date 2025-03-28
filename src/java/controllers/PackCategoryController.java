/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import business.Pack;
import business.User;
import business.PackCategory;
import data.PackDA;
import data.CategoryDA;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author am528423
 */
public class PackCategoryController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(Category.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Users loggedInUser = (Users) request.getSession().getAttribute("loggedInUser");
        Account activeAccount = (Account) request.getSession().getAttribute("activeAccount");

        if (loggedInUser == null) {
            response.sendRedirect("Public");
            return;
        }

        LocalDate createdDate = LocalDate.now();

        String url = "/expense.jsp";
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        HashMap<String, String> errors = new HashMap();

        String message;

        //get and set expenselist 
        LinkedHashMap<Integer, Category> categoryList = new LinkedHashMap();

        try {
            categoryList = CategoryDA.selectAllCategoriesByAccountID(activeAccount.getAccountID());
        } catch (NamingException | SQLException ex) {
            errors.put("categoryList", "Trouble getting category list");
        }

        request.setAttribute("categoryList", categoryList);

        switch (action) {
            case "accountPage" -> {

                request.setAttribute("loggedInUser", loggedInUser);
                break;
            }

            case "addCategory" -> {
                String categoryName = request.getParameter("newCategoryName");
                double budgetAmount = Double.parseDouble(request.getParameter("newBudgetAmount"));

                Category category = new Category();
                category.setCategoryName(categoryName);
                category.setBudgetAmount(budgetAmount);
                category.setAccountID(activeAccount.getAccountID());
                category.setCreatedDate(createdDate);

                try {
                    if (!category.getCategoryName().equals(CategoryDA.doesCategoryNameExists(categoryName))) {
                        CategoryDA.addCategory(category, category.getAccountID());
                        message = "Category created successfully.";

                    } else {
                        message = "Category Name already exists.";
                        errors.put("Category Name", "already exists");
                    }
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Naming & SQL error. Check DB.";
                }
                try {
                    categoryList = CategoryDA.selectAllCategoriesByAccountID(activeAccount.getAccountID());
                } catch (NamingException | SQLException ex) {
                    errors.put("categoryList", "Trouble getting category list");
                }
                request.setAttribute("accountCategories", categoryList);
                request.setAttribute("errors", errors);
                request.setAttribute("message", message);
                request.getSession().setAttribute("loggedInUser", loggedInUser);
                break;
            }

            case "editCategory" -> {
                int categoryID = Integer.parseInt(request.getParameter("categoryID"));
                String categoryName = request.getParameter("updatedCategoryName");
                double budgetAmount = Double.parseDouble(request.getParameter("updatedBudgetAmount"));

                Category category = new Category();

                category.setCategoryID(categoryID);
                category.setCategoryName(categoryName);
                category.setBudgetAmount(budgetAmount);

                try {
                    if (!category.getCategoryName().equals(CategoryDA.doesCategoryNameExists(categoryName))) {
                        CategoryDA.editCategory(category);
                        message = "Category Updated.";
                    } else {
                        message = "Category Name already exists.";
                        errors.put("Category Name", "already exists");
                    }
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Naming & SQL error. Check DB.";

                }
                try {
                    categoryList = CategoryDA.selectAllCategoriesByAccountID(activeAccount.getAccountID());
                } catch (NamingException | SQLException ex) {
                    errors.put("categoryList", "Trouble getting category list");
                }
                request.setAttribute("accountCategories", categoryList);
                request.setAttribute("message", message);
                request.setAttribute("loggedInUser", loggedInUser);
                break;
            }

            case "deleteCategory" -> {

                String key = request.getParameter("key");
                int delete = Integer.parseInt(key);
                try {
                    CategoryDA.deleteCategory(delete);
                    message = "Category Deleted.";
                    request.setAttribute("message", message);
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Naming & SQL error. Check DB.";
                    request.setAttribute("message", message);
                }
                try {
                    categoryList = CategoryDA.selectAllCategoriesByAccountID(activeAccount.getAccountID());
                } catch (NamingException | SQLException ex) {
                    errors.put("categoryList", "Trouble getting category list");
                }
                request.setAttribute("accountCategories", categoryList);
                request.setAttribute("loggedInUser", loggedInUser);
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

