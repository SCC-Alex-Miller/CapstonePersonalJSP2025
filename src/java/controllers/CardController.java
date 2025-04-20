/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import business.Card;
import business.Pack;
import business.User;
import data.CardDA;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
public class CardController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(Card.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        Pack activePack = (Pack) request.getSession().getAttribute("activePack");

        if (loggedInUser == null) {
            response.sendRedirect("Public");
            return;
        }

        LinkedHashMap<String, String> errors = new LinkedHashMap();

        String url = "/individualPack.jsp";
        String message = "";

        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        //get and set expenselist 
        LinkedHashMap<Integer, Card> packCards = new LinkedHashMap();

        try {
            packCards = CardDA.selectPackCards(activePack.getPackID());
        } catch (NamingException | SQLException e) {
            LOG.log(Level.SEVERE, url, e);
            message = "Problem getting expense list";
        }

        request.setAttribute("packCards", packCards);

        switch (action) {
            case "addCard" -> {

                String cardQuestion = request.getParameter("cardQuestion");
                String cardAnswer = request.getParameter("cardAnswer");

                Card card = new Card();

                card.setCardQuestion(cardQuestion);
                card.setCardAnswer(cardAnswer);

                if (card.getCardQuestion() == null || card.getCardQuestion().equals("")) {
                    errors.put("cardQuestion", "Card Question empty");
                }
                
                if (card.getCardAnswer() == null || card.getCardAnswer().equals("")) {
                    errors.put("cardAnswer", "Card Answer empty");
                }

                //add fkAccountID to expense
                card.setFkPackID(activePack.getPackID());

                //Inserting expense to database
                if (errors.isEmpty()) {
                    try {
                        CardDA.addCard(card);
                    } catch (NamingException | SQLException e) {
                        LOG.log(Level.SEVERE, url, e);
                        message = "Problem with adding card";
                    }

                }

                //Reset expenseList to dynamically load
                try {
                    packCards = CardDA.selectPackCards(activePack.getPackID());
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Problem getting expense list for dynamic load";
                }

                request.setAttribute("packCards", packCards);
                request.setAttribute("errors", errors);

                break;
            }
            
            case "deleteCard" -> {
                int cardID = Integer.parseInt(request.getParameter("cardID"));

                try {
                    CardDA.deleteCard(cardID);
                } catch (NamingException | SQLException ex) {
                    errors.put("pack", "Trouble deleting card");
                }
                
                url = "/individualPack.jsp";
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
