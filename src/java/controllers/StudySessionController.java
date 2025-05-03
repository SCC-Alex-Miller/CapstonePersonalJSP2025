/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.Card;
import business.Pack;
import business.StudySession;
import business.User;
import data.CardDA;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lando
 */
@WebServlet(name = "StudySessionController", urlPatterns = {"/StudySessionController"})
public class StudySessionController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        Pack activePack = (Pack) request.getSession().getAttribute("activePack");

        if (loggedInUser == null) {
            response.sendRedirect("Public");
            return;
        }

        String url = "/studySession.jsp";
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        HashMap<String, String> errors = new HashMap<>();
        String message = "";

        LinkedHashMap<Integer, Card> packCards = new LinkedHashMap<>();
        try {
            packCards = CardDA.selectPackCards(activePack.getPackID());
        } catch (NamingException | SQLException ex) {
            errors.put("packCards", "Trouble getting pack cards");
        }
        request.setAttribute("packCards", packCards);

        switch (action) {
            case "startSession": {
                List<Card> cards = new ArrayList<>(packCards.values());

                session.setAttribute("cards", cards);
                session.setAttribute("currentCardIndex", 0);
                session.setAttribute("correctCount", 0);
                session.setAttribute("wrongCount", 0);

                if (!cards.isEmpty()) {
                    request.setAttribute("currentCard", cards.get(0));
                    message = "GO!";
                }

                request.setAttribute("message", message);
                request.setAttribute("errors", errors);
                request.getSession().setAttribute("loggedInUser", loggedInUser);
                break;
            }

            case "submitAnswer": {
                String result = request.getParameter("result"); // "right" or "wrong"
                Integer index = (Integer) session.getAttribute("currentCardIndex");
                List<Card> cards = (List<Card>) session.getAttribute("cards");

                if ("right".equals(result)) {
                    session.setAttribute("correctCount", ((int) session.getAttribute("correctCount")) + 1);
                } else if ("wrong".equals(result)) {
                    session.setAttribute("wrongCount", ((int) session.getAttribute("wrongCount")) + 1);
                }

                index++;
                session.setAttribute("currentCardIndex", index);

                if (index < cards.size()) {
                    request.setAttribute("currentCard", cards.get(index));
                } else {
                    // End session
                    StudySession studySession = new StudySession();
                    studySession.setSessionRight((int) session.getAttribute("correctCount"));
                    studySession.setSessionWrong((int) session.getAttribute("wrongCount"));

                    request.setAttribute("sessionComplete", true);
                    request.setAttribute("studySession", studySession);
                }

                break;
            }

            default:
                break;
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
