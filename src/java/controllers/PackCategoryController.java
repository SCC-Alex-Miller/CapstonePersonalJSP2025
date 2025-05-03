/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import business.Pack;
import business.User;
import business.PackCategory;
import data.PackDA;
import data.PackCategoryDA;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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

    private static final Logger LOG = Logger.getLogger(PackCategory.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        //Account activeAccount = (Account) request.getSession().getAttribute("activeAccount");

        if (loggedInUser == null) {
            response.sendRedirect("Public");
            return;
        }

        LocalDate createdDate = LocalDate.now();

        String url = "/userPack.jsp";
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        HashMap<String, String> errors = new HashMap();

        String message;
 
        LinkedHashMap<Integer, PackCategory> packCategoryList = new LinkedHashMap();

        try {
            packCategoryList = PackCategoryDA.selectAllPackCategoriesByUserID(loggedInUser.getUserID());
        } catch (NamingException | SQLException ex) {
            errors.put("packCategoryList", "Trouble getting pack category list");
        }

        request.setAttribute("packCategoryList", packCategoryList);

        switch (action) {
            case "accountPage" -> {

                request.setAttribute("loggedInUser", loggedInUser);
                break;
            }

            case "addPackCategory" -> {
                String packCategoryName = request.getParameter("newPackCategoryName");

                PackCategory packCategory = new PackCategory();
                packCategory.setPackCategoryName(packCategoryName);
                packCategory.setFkUserID(loggedInUser.getUserID());
                packCategory.setPackCategoryCreatedDate(createdDate);

                try {
                    if (PackCategoryDA.doesPackCategoryNameExists(packCategoryName, loggedInUser.getUserID()) == false) {
                        PackCategoryDA.addPackCategory(packCategory, packCategory.getFkUserID());
                        message = "Pack Category created successfully.";

                    } else {
                        message = "Pack Category Name already exists.";
                        errors.put("Pack Category Name", "already exists");
                    }
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Naming & SQL error. Check DB.";
                }
                try {
                    packCategoryList = PackCategoryDA.selectAllPackCategoriesByUserID(loggedInUser.getUserID());
                } catch (NamingException | SQLException ex) {
                    errors.put("packCategoryList", "Trouble getting pack category list");
                }
                request.setAttribute("packCategoryList", packCategoryList);
                request.setAttribute("errors", errors);
                request.setAttribute("message", message);
                request.getSession().setAttribute("loggedInUser", loggedInUser);
                break;
            }

            case "editPackCategory" -> {
                int packCategoryID = Integer.parseInt(request.getParameter("packCategoryID"));
                String packCategoryName = request.getParameter("editedPackCategoryName");

                PackCategory packCategory = new PackCategory();

                packCategory.setPackCategoryID(packCategoryID);
                packCategory.setPackCategoryName(packCategoryName);

                try {
                    if (PackCategoryDA.doesPackCategoryNameExists(packCategoryName, loggedInUser.getUserID()) == false) {
                        PackCategoryDA.editPackCategory(packCategory);
                        message = "Pack Category Updated.";
                    } else {
                        message = "Pack Category Name Already Exists.";
                        errors.put("Pack Category Name", "Already Exists");
                    }
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Naming & SQL error. Check DB.";

                }
                try {
                    packCategoryList = PackCategoryDA.selectAllPackCategoriesByUserID(loggedInUser.getUserID());
                } catch (NamingException | SQLException ex) {
                    errors.put("packCategoryList", "Trouble getting pack category list");
                }
                request.setAttribute("packCategoryList", packCategoryList);
                request.setAttribute("message", message);
                request.setAttribute("loggedInUser", loggedInUser);
                break;
            }

            case "deletePackCategory" -> {

                String key = request.getParameter("key");
                int deleteKey = Integer.parseInt(key);
                try {
                    PackCategoryDA.deletePackCategory(deleteKey);
                    message = "Pack Category Deleted.";
                    request.setAttribute("message", message);
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Naming & SQL error. Check DB.";
                    request.setAttribute("message", message);
                }
                try {
                    packCategoryList = PackCategoryDA.selectAllPackCategoriesByUserID(loggedInUser.getUserID());
                } catch (NamingException | SQLException ex) {
                    errors.put("categoryList", "Trouble getting category list");
                }
                request.setAttribute("packCategoryList", packCategoryList);
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

