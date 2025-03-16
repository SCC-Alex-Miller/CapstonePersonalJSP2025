/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.Account;
import business.Category;
import business.Users;
import data.AccountDA;
import data.CategoryDA;
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
 * @author sm405555
 */
public class AccountController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LocalDateTime currentDateTime = LocalDateTime.now();

        Users loggedInUser = (Users) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            response.sendRedirect("Public");
            return;
        }

        LinkedHashMap<Integer, Account> allUserAccounts = new LinkedHashMap<>();
        HashMap<String, String> errors = new HashMap();
        String message;

        String url = "/account.jsp";

        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }

        switch (action) {
            case "accountPage" -> {
                break;
            }

            case "createAccount" -> {

                String accountName = request.getParameter("newAccount");

                Account account = new Account();
                account.setAccountID(0);
                account.setAccountName(accountName);
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

            case "checkoutAccount" -> {
                String accountId = request.getParameter("accountId");
                String accountName = request.getParameter("accountName");

                int parsedAccountId = Integer.parseInt(accountId);

                Account activeAccount = new Account();
                activeAccount.setAccountID(parsedAccountId);
                activeAccount.setAccountName(accountName);
                activeAccount.setUser(loggedInUser);

                LinkedHashMap<Integer, Category> accountCategories = new LinkedHashMap<>();
                LinkedHashMap<Integer, Category> sortedCategories = new LinkedHashMap<>();
                try {
                    accountCategories = CategoryDA.selectAllCategoriesByAccountID(parsedAccountId);
                    sortedCategories = accountCategories.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.comparing(Category::getCategoryName)))
                            .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), Map::putAll);
                } catch (NamingException | SQLException ex) {
                    LOG.log(Level.SEVERE, url, ex);
                    message = "Naming & SQL error. Check DB.";
                }

                request.getSession().setAttribute("activeAccount", activeAccount);
                request.getSession().setAttribute("accountCategories", sortedCategories);
                url = "/Expense";
                break;
            }

            case "logOut" -> {

                if (request.getSession() != null) {
                    request.getSession().invalidate();
                    response.sendRedirect("index.jsp");
                    return;
                } else {
                    message = "Unable to Logout";
                    request.setAttribute("message", message);
                }
                break;
            }

            case "updateAccount" -> {
                String accountId = request.getParameter("accountId");
                String incomingAccountName = request.getParameter("updatedAccountName");

                int update = Integer.parseInt(accountId);

                Account account = new Account();
                account.setUser(loggedInUser);

                List<Account> allAccountsOtherThanIncoming = allUserAccounts.values().stream().filter(a -> a.getAccountID() != update).collect(Collectors.toList());
                boolean canWeUpdate = allAccountsOtherThanIncoming.stream().anyMatch(a -> a.getAccountName().equalsIgnoreCase(incomingAccountName));

                try {
                    if (!canWeUpdate) {
                        AccountDA.updateAccount(update, incomingAccountName);
                        message = "Account Name Updated.";

                    } else {
                        message = "Accountname already exists amongst other accounts.";
                        errors.put("accountname", "already exists");
                    }

                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Naming & SQL error. Check DB.";
                }
                request.setAttribute("message", message);
                break;
            }

            case "deleteAccount" -> {

                String key = request.getParameter("key");
                int delete = Integer.parseInt(key);
                try {
                    AccountDA.deleteAccount(delete);
                    message = "Account Deleted.";
                } catch (NamingException | SQLException e) {
                    LOG.log(Level.SEVERE, url, e);
                    message = "Naming & SQL error. Check DB.";
                }
                request.setAttribute("message", message);
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
