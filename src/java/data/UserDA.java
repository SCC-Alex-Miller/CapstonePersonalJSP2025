/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Users;
import javax.naming.NamingException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;


/**
 *
 * @author kkmil
 */
public class UserDA {
    public static int insertUser(Users user) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO users (firstName, lastName, email, password, createdDate) "
                + "VALUES (?, ?, ?, ?, ?)";

        ps = connection.prepareStatement(query);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPassword());
        ps.setDate(5, Date.valueOf(user.getCreatedDate()));

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;

    }

    public static int updateUser(Users user) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "UPDATE users "
                + "SET firstName = ?, lastName = ?, email = ?, password = ?, createdDate = ?"
                + "WHERE userID = ?";

        ps = connection.prepareStatement(query);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPassword());
        ps.setDate(5, Date.valueOf(user.getCreatedDate()));
        ps.setInt(6, user.getUserID());

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;

    }

    
    public static LinkedHashMap<Integer, Users> selectUsers() throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM users";

        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();

        LinkedHashMap<Integer, Users> users = new LinkedHashMap();
        while (rs.next()) {
            Users user = new Users();
            user.setUserID(rs.getInt("userID"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setCreatedDate(rs.getDate("createdDate").toLocalDate());
            users.put(user.getUserID(), user);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return users;

    }

    public static Users getUserFromUserID(int userID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT * FROM users "
                + "WHERE userID = ? ";

        ps = connection.prepareStatement(query);
        ps.setInt(1, userID);
        rs = ps.executeQuery();
        
        Users user = null;
        if (rs.next()) {
            user = new Users();
            user.setUserID(rs.getInt("userID"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setCreatedDate(rs.getDate("createdDate").toLocalDate());
            return user;
        }
        
        rs.close();
        ps.close();
        pool.freeConnection(connection);
        return user;
    }

    public static Users getPasswordFromEmail(String email) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query
                = "SELECT * FROM users "
                + "WHERE email = ? ";

        ps = connection.prepareStatement(query);
        ps.setString(1, email);
        rs = ps.executeQuery();

        Users user = null;
        if (rs.next()) {
            user = new Users();
            user.setUserID(rs.getInt("userId"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
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
                = "SELECT * FROM users "
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
