/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import business.Card;
import business.Pack;
import business.PackCategory;
import business.User;
import data.CardDA;
import data.PackCategoryDA;
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
        String error;

        String url = "/userPack.jsp";

        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "userPackPage" -> {
                break;
            }

            case "publicPackPage" -> {

                url = "/publicPack.jsp";
                break;
            }

            case "editPack" -> {
                int packID = Integer.parseInt(request.getParameter("packID"));
                boolean isPublic = false;
                String packName = request.getParameter("updatedPackName");
                int packCategoryID = Integer.parseInt(request.getParameter("newPackCategoryID"));
                if (request.getParameter("isPublic") != null) {
                    isPublic = true;
                }

                Pack pack = new Pack();

                pack.setPackID(packID);
                pack.setPackName(packName);
                pack.setIsPublic(isPublic);
                pack.setPackCategoryID(packCategoryID);

                Pack originalPack = new Pack();

                try {
                    boolean packNameExists = PackDA.doesPackNameExists(packName, loggedInUser.getUserID());
                    if (!packNameExists) {
                        PackDA.editPack(pack);
                        message = "Pack edited successfully.";
                    } else {
                        originalPack = PackDA.selectPack(packID);
                        if (originalPack.getPackName().equals(pack.getPackName())) {
                            PackDA.editPack(pack);
                            message = "Pack edited successfully.";
                        } else {
                            message = "packName already exists.";
                            errors.put("packName", "already exists");
                        }
                    }
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Unable to edit pack.";
                }

                url = "/userPack.jsp";
                break;
            }

            case "downloadPack" -> {
                int packID = Integer.parseInt(request.getParameter("packID"));

                //Grab the pack
                Pack pack = new Pack();

                try {
                    pack = PackDA.selectPack(packID);
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Unable to locate pack.";
                }

                //Grab the PackCategory
                PackCategory category = new PackCategory();

                try {
                    category = PackCategoryDA.selectPackCategory(pack.getPackCategoryID());
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Unable to get PackCategory.";
                }

                category.setFkUserID(loggedInUser.getUserID());

                //Add the PackCategory
                try {
                    if (PackCategoryDA.doesPackCategoryNameExists(category.getPackCategoryName(), loggedInUser.getUserID()) == false) {
                        PackCategoryDA.addPackCategory(category, loggedInUser.getUserID());
                        message = "Pack Category created successfully.";

                    } else {
                        message = "Pack Category Name already exists.";
                        errors.put("Pack Category Name", "already exists");
                    }
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Naming & SQL error. Check DB.";
                }

                //Add the Pack
                pack.setUser(loggedInUser);
                pack.setPackHighScore(0);
                pack.setPackHighScoreTime("00:00:00");

                String packName = pack.getPackName();

                Boolean makeCards = false;

                try {
                    boolean packNameExists = PackDA.doesPackNameExists(packName, loggedInUser.getUserID());
                    if (!packNameExists) {
                        PackDA.addPack(pack);
                        message = "Pack created successfully.";
                        makeCards = true;
                    } else {
                        message = "packName already exists.";
                        errors.put("packName", "already exists");
                    }
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Unable to add pack.";
                }

                //Get cards for pack
                LinkedHashMap<Integer, Card> packCards = new LinkedHashMap();

                try {
                    packCards = CardDA.selectPackCards(pack.getPackID());
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Unable to get cards.";
                }

                //Get PackID for the pack you just added
                Pack newPack = new Pack();
                try {
                    newPack = PackDA.selectPack(loggedInUser.getUserID(), packName);
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Unable to get packID.";
                }

                //Add Cards to pack
                if (makeCards == true) {
                    try {
                        for (Card card : packCards.values()) {
                            card.setFkPackID(newPack.getPackID());
                            CardDA.addCard(card);
                        }
                    } catch (NamingException | SQLException e) {
                        LOG.log(Level.SEVERE, url, e);
                        message = "Unable to get cards.";
                    }
                }

                url = "/userPack.jsp";
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
                    boolean alreadyCreated = PackDA.doesPackNameExists(packName, loggedInUser.getUserID());

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

                request.setAttribute("rightCount", 0);
                request.setAttribute("wrongCount", 0);
                request.setAttribute("currentIndex", 0);
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
