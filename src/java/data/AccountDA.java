/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Account;
import business.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author spenc
 */
public class AccountDA {

    public static boolean doesAccountnameExists(String accountName, int fkUserId) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT accountName FROM account "
                + "WHERE accountName = ? AND fkUserId = ?";

        ps = connection.prepareStatement(query);
        ps.setString(1, accountName);
        ps.setInt(2, fkUserId);

        rs = ps.executeQuery();

        boolean b = rs.next();

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return b;

    }

    //See all Accounts
    public static LinkedHashMap<Integer, Account> seeAllUserSpecificAccounts(Account account) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM account "
                + "WHERE fkUserId = ?";

        ps = connection.prepareStatement(query);
        ps.setInt(1, account.getUser().getUserID());
        rs = ps.executeQuery();

        LinkedHashMap<Integer, Account> allUserAccounts = new LinkedHashMap();
        while (rs.next()) {
            Account accounts = new Account();
            Users user = new Users();
            accounts.setAccountID(rs.getInt("accountId"));
            accounts.setAccountName(rs.getString("accountName"));
            user.setUserID(rs.getInt("fkUserId"));
            accounts.setUser(user);
            accounts.setCreatedDate(rs.getTimestamp("createdDate").toLocalDateTime());

            allUserAccounts.put(accounts.getAccountID(), accounts);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return allUserAccounts;

    }

    //Checkout Specific Account
    public static LinkedHashMap<Integer, Account> seeSelectedAccount(Account account) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM account "
                + "WHERE accountId = ?";

        ps = connection.prepareStatement(query);
        ps.setInt(1, account.getAccountID());
        rs = ps.executeQuery();

        LinkedHashMap<Integer, Account> allUserAccounts = new LinkedHashMap();
        while (rs.next()) {
            Account accounts = new Account();
            Users user = new Users();
            accounts.setAccountID(rs.getInt("accountId"));
            accounts.setAccountName(rs.getString("accountName"));
            user.setUserID(rs.getInt("fkUserId"));
            accounts.setUser(user);
            accounts.setCreatedDate(rs.getTimestamp("createdDate").toLocalDateTime());

            allUserAccounts.put(accounts.getAccountID(), accounts);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return allUserAccounts;

    }
    
    public static int getAccountIDByNameAndUserID(String accountName, int userID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT accountId FROM account "
                + "WHERE fkUserId = ? AND accountName = ?";

        ps = connection.prepareStatement(query);
        ps.setInt(1, userID);
        ps.setString(2, accountName);
        rs = ps.executeQuery();

        int accountID = 0;
        while (rs.next()) {
            accountID = rs.getInt("accountId");
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return accountID;

    }

    //Create Account
    public static int createAccount(Account account) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;

        String query
                = "INSERT INTO account (accountId, accountName, fkUserId, createdDate) "
                + "VALUES (?, ?, ?, ?)";

        ps = connection.prepareStatement(query);
        ps.setInt(1, account.getAccountID());
        ps.setString(2, account.getAccountName());
        ps.setInt(3, account.getUser().getUserID());
        ps.setTimestamp(4, Timestamp.valueOf(account.getCreatedDate()));

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;

    }

    //Update Account
    public static int updateAccount(int accountId, String accountName) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;

        String query = "UPDATE account SET "
                + "accountName = ? "
                + "WHERE accountId = ? ";

        ps = connection.prepareStatement(query);
        ps.setString(1, accountName);
        ps.setInt(2, accountId);

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;
    }

    //Account Deletion
    public static int deleteAccount(int accountId) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;

        String query = "DELETE from account "
                + "WHERE accountId = ?";

        ps = connection.prepareStatement(query);
        ps.setInt(1, accountId);

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;
    }

}