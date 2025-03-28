/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import business.Pack;
import business.User;
import data.PackDA;
import java.io.IOException;
import java.sql.SQLException;
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

        LocalDateTime currentDateTime = LocalDateTime.now();

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

                Pack pack = new Pack();
                
                pack.setPackName(packName);
                account.setUser(loggedInUser);
                account.setCreatedDate(currentDateTime);

                try {
                    boolean alreadyCreated = AccountDA.doesAccountnameExists(accountName, account.getUser().getUserID());

                    if (!alreadyCreated) {
                        AccountDA.createAccount(account);
                        message = "Account created successfully.";

                        //UNABLE TO SEED DATA???
                        int userAccountID = AccountDA.getAccountIDByNameAndUserID(account.getAccountName(), account.getUser().getUserID());
                        CategoryDA.seedDefaultCategories(userAccountID);
                    } else {
                        message = "Accountname already exists.";
                        errors.put("accountname", "already exists");
                    }
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Naming & SQL error. Check DB.";
                }

                request.setAttribute("errors", errors);
                request.setAttribute("message", message);
                break;
            }

        }
    }
}
