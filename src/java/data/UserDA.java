/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.User;
import javax.naming.NamingException;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;


/**
 *
 * @author kkmil
 */
public class UserDA {
    public static int insertUser(User user) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO user (username, email, password, role, activeStatus, adminMessage, createdDate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        ps = connection.prepareStatement(query);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getRole());
        ps.setBoolean(5, user.isActiveStatus());
        ps.setString(6, user.getAdminMessage());
        ps.setDate(7, Date.valueOf(user.getCreatedDate()));

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;

    }

    public static int updateUser(User user) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "UPDATE user "
                + "SET username = ?, email = ?, password = ?"
                + "WHERE userID = ?";

        ps = connection.prepareStatement(query);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setInt(4, user.getUserID());

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;

    }

    
    public static LinkedHashMap<Integer, User> selectUsers() throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user";

        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();

        LinkedHashMap<Integer, User> users = new LinkedHashMap();
        while (rs.next()) {
            User user = new User();
            user.setUserID(rs.getInt("userID"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setActiveStatus(rs.getBoolean("activeStatus"));
            user.setAdminMessage(rs.getString("adminMessage"));
            user.setCreatedDate(rs.getDate("createdDate").toLocalDate());
            users.put(user.getUserID(), user);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return users;

    }

    public static User getUserFromUserID(int userID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT * FROM user "
                + "WHERE userID = ? ";

        ps = connection.prepareStatement(query);
        ps.setInt(1, userID);
        rs = ps.executeQuery();
        
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setUserID(rs.getInt("userID"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setActiveStatus(rs.getBoolean("activeStatus"));
            user.setAdminMessage(rs.getString("adminMessage"));
            user.setCreatedDate(rs.getDate("createdDate").toLocalDate());
            return user;
        }
        
        rs.close();
        ps.close();
        pool.freeConnection(connection);
        return user;
    }

    public static User getPasswordFromEmail(String email) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT * FROM user "
                + "WHERE email = ? ";

        ps = connection.prepareStatement(query);
        ps.setString(1, email);
        rs = ps.executeQuery();

        User user = null;
        if (rs.next()) {
            user = new User();
            user.setUserID(rs.getInt("userID"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setActiveStatus(rs.getBoolean("activeStatus"));
            user.setAdminMessage(rs.getString("adminMessage"));
            user.setCreatedDate(rs.getDate("createdDate").toLocalDate());
            return user;
        }
        
        rs.close();
        ps.close();
        pool.freeConnection(connection);
        return user;
    }
    
    public static boolean emailExists(String email) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT * FROM user "
                + "WHERE email = ? ";

        ps = connection.prepareStatement(query);
        ps.setString(1, email);
        rs = ps.executeQuery();

        boolean emailExists = rs.next();
        
        rs.close();
        ps.close();
        pool.freeConnection(connection);
        return emailExists;
    }

}
