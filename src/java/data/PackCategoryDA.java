/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.PackCategory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author lando
 */
public class PackCategoryDA {
    
    public static LinkedHashMap<Integer, PackCategory> selectAllPackCategoriesByUserID (int userID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM packcategory"
                + " WHERE fkUserID = ?;";
        
        ps = connection.prepareStatement(query);
        ps.setInt(1, userID);
        rs = ps.executeQuery();
        
        LinkedHashMap<Integer, PackCategory> packCategories = new LinkedHashMap();
        while (rs.next()) {
            PackCategory packCategory = new PackCategory();
            packCategory.setPackCategoryID(rs.getInt("packCategoryID"));
            packCategory.setPackCategoryName(rs.getString("packCategoryName"));
            packCategory.setPackCategoryCreatedDate(rs.getDate("packCategoryCreatedDate").toLocalDate());
            packCategory.setFkUserID(userID);

            packCategories.put(packCategory.getPackCategoryID(), packCategory);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return packCategories;
    }
    
    public static PackCategory selectPackCategory(int packCategoryID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM packcategory WHERE packCategoryID = ?;";
        ps = connection.prepareStatement(query);
        ps.setInt(1, packCategoryID);
        rs = ps.executeQuery();

        PackCategory selectedPackCategory = new PackCategory();
        if (rs.next()) {
            selectedPackCategory.setPackCategoryID(rs.getInt("packCategoryID"));
            selectedPackCategory.setPackCategoryName(rs.getString("packCategoryName"));
            selectedPackCategory.setPackCategoryCreatedDate(rs.getDate("packCategoryCreatedDate").toLocalDate());
            selectedPackCategory.setFkUserID(rs.getInt("fkUserID"));
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return selectedPackCategory;
    }
    
    public static boolean doesPackCategoryNameExists(String packCategoryName, int fkUserID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT packCategoryName FROM packcategory "
                + "WHERE packCategoryName = ? AND fkUserID = ? ";

        ps = connection.prepareStatement(query);
        ps.setString(1, packCategoryName);
        ps.setInt(2, fkUserID);
        rs = ps.executeQuery();

        boolean b = rs.next();

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return b;

    }
    
    public static int addPackCategory(PackCategory packCategory, int userID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO packcategory (packCategoryName, packCategoryCreatedDate, fkUserID) VALUES (?, ?, ?);";
        ps = connection.prepareStatement(query);

        ps.setString(1, packCategory.getPackCategoryName());
        ps.setDate(2, Date.valueOf(packCategory.getPackCategoryCreatedDate()));
        ps.setInt(3, userID);

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;
    }
    
    public static int editPackCategory(PackCategory packCategory) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;

        String query = "UPDATE packcategory SET packCategoryName = ?, WHERE packCategoryID = ?;";

        ps = connection.prepareStatement(query);
        ps.setString(1, packCategory.getPackCategoryName());
        ps.setInt(2, packCategory.getPackCategoryID());

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;
    }
    
    public static int deletePackCategory(int packCategoryID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM packcategory "
                + "WHERE packCategoryID = ?";
        ps = connection.prepareStatement(query);

        ps.setInt(1, packCategoryID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);
        return rows;
    }
    
    
}
