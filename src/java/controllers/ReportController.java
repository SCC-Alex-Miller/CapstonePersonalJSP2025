/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.Card;
import business.Pack;
import business.Report;
import business.User;
import data.CardDA;
import data.ReportDA;
import data.UserDA;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * @author lando
 */
public class ReportController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(Report.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

        if (loggedInUser == null) {
            response.sendRedirect("Public");
            return;
        }

        String url = "/publicPack.jsp";
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        HashMap<String, String> errors = new HashMap();

        String message;
        String error;

        switch (action) {
            case "goToReportPage" -> {
                request.setAttribute("loggedInUser", loggedInUser);
                String reportedUsername = request.getParameter("reportedUsername");
                int reportedUserID = Integer.parseInt(request.getParameter("reportedUserID"));
                int reportedPackID = Integer.parseInt(request.getParameter("reportedPackID"));

                request.setAttribute("reportedUsername", reportedUsername);
                request.setAttribute("reportedUserID", reportedUserID);
                request.setAttribute("reportedPackID", reportedPackID);

                url = "/reportPage.jsp";
                break;
            }

            case "addReport" -> {
                request.setAttribute("loggedInUser", loggedInUser);
                int reportedUserID = Integer.parseInt(request.getParameter("reportedUserID"));
                int reportedPackID = Integer.parseInt(request.getParameter("reportedPackID"));

                String reportType = request.getParameter("reportType");
                String reportUserNotes = request.getParameter("reportUserNotes");
                int reportCreatedByID = loggedInUser.getUserID();

                Report report = new Report();

                report.setReportType(reportType);
                report.setReportActive(true);
                report.setReportUserNotes(reportUserNotes);
                report.setReportCreatedByID(reportCreatedByID);
                report.setReportedUserID(reportedUserID);
                report.setReportedPackID(reportedPackID);

                try {
                    ReportDA.addReport(report);
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Unable to add report";
                }

                url = "/publicPack.jsp";
                break;
            }

            case "goToActiveReports" -> {
                if (loggedInUser.getIsAdmin() == false) {
                    response.sendRedirect("Pack");
                    return;
                }

                url = "/activeReports.jsp";
                break;
            }

            case "viewPack" -> {
                int packID = Integer.parseInt(request.getParameter("packID"));
                int reportedUserID = Integer.parseInt(request.getParameter("reportedUserID"));
                String packName = request.getParameter("packName");

                User reportedUser = new User();

                reportedUser.setUserID(reportedUserID);

                Pack activePack = new Pack();
                activePack.setPackID(packID);
                activePack.setPackName(packName);
                activePack.setUser(reportedUser);

                LinkedHashMap<Integer, Card> packCards = new LinkedHashMap();

                try {
                    packCards = CardDA.selectPackCards(activePack.getPackID());
                    request.setAttribute("packCards", packCards);

                } catch (NamingException | SQLException e) {
                    error = "Issue populating cards for Pack.";
                }

                request.setAttribute("packCards", packCards);
                request.getSession().setAttribute("activePack", activePack);
                url = "/individualPack.jsp";
                break;
            }

            case "editReport" -> {
                int reportID = Integer.parseInt(request.getParameter("reportID"));
                String adminNotes = request.getParameter("adminNotes");
                String answer = request.getParameter("answer");

                if (answer.equals("approve")) {
                    try {
                        ReportDA.editReport(reportID, adminNotes, false);
                    } catch (NamingException | SQLException e) {
                        error = "Unable to edit report";
                    }

                    int reportStrikes = loggedInUser.getReportStrikes();

                    reportStrikes++;

                    loggedInUser.setReportStrikes(reportStrikes);

                    try {
                        UserDA.changeReportStrike(loggedInUser);
                    } catch (NamingException | SQLException e) {
                        error = "Unable to change reportStrikes";
                    }
                } else if (answer.equals("reject")) {
                    try {
                        ReportDA.editReport(reportID, adminNotes, false);
                    } catch (NamingException | SQLException e) {
                        error = "Unable to edit report.";
                    }
                }
                url = "/activeReports.jsp";
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
