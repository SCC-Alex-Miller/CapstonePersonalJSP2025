/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import business.Card;
import business.Pack;
import business.User;
import data.CardDA;
import data.PackDA;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lando
 */
public class PackController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(PackController.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LocalDate currentDateTime = LocalDate.now();

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            response.sendRedirect("Public");
            return;
        }

        LinkedHashMap<Integer, Pack> allUserPacks = new LinkedHashMap<>();
        HashMap<String, String> errors = new HashMap();
        String message;

        String url = "/userPack.jsp";

        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "userPackPage" -> {
                break;
            }

            case "addPack" -> {

                String packName = request.getParameter("newPack");
                int packCategoryID = Integer.parseInt(request.getParameter("packCategory"));

                Pack pack = new Pack();

                pack.setPackName(packName);
                pack.setPackCategoryID(packCategoryID);
                pack.setPackHighScore(0);
                pack.setPackHighScoreTime("00:00:00");
                pack.setUser(loggedInUser);
                pack.setCreatedDate(currentDateTime);

                try {
                    boolean alreadyCreated = PackDA.doesPackNameExists(packName);

                    if (!alreadyCreated) {
                        PackDA.addPack(pack);
                        message = "Pack created successfully.";
                    } else {
                        message = "packName already exists.";
                        errors.put("packName", "already exists");
                    }
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Naming & SQL error. Check DB.";
                }

                request.setAttribute("errors", errors);
                request.setAttribute("message", message);
                break;
            }

            case "viewPack" -> {
                int packID = Integer.parseInt(request.getParameter("packID"));
                String packName = request.getParameter("packName");

                Pack activePack = new Pack();
                activePack.setPackID(packID);
                activePack.setPackName(packName);
                activePack.setUser(loggedInUser);

                request.getSession().setAttribute("activePack", activePack);
                url = "/individualPack.jsp";
                break;
            }

            case "studyPack" -> {
                int packID = Integer.parseInt(request.getParameter("packID"));
                String packName = request.getParameter("packName");

                Pack activePack = new Pack();
                activePack.setPackID(packID);
                activePack.setPackName(packName);
                activePack.setUser(loggedInUser);

                request.getSession().setAttribute("activePack", activePack);

                LinkedHashMap<Integer, Card> packCards = new LinkedHashMap();

                try {
                    packCards = CardDA.selectPackCards(activePack.getPackID());
                } catch (NamingException | SQLException ex) {
                    errors.put("packCards", "Trouble getting pack cards for study");
                }

                request.setAttribute("packCards", packCards);
                url = "/studySession.jsp";
                break;
            }

            case "deletePack" -> {
                int packID = Integer.parseInt(request.getParameter("packID"));

                try {
                    PackDA.deletePack(packID);
                } catch (NamingException | SQLException ex) {
                    errors.put("pack", "Trouble deleting pack");
                }
                
                url = "/userPack.jsp";
                break;
            }

        }

        getServletContext()
                .getRequestDispatcher(url).forward(request, response);
    }

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
        return "Account Creation and Checkout";
    }
}
