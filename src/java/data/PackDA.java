/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Pack;
import business.User;
import business.PackCategory;
import data.PackCategoryDA;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author lando
 */
public class PackDA {
    
    public static LinkedHashMap<Integer, Pack> seeAllUserPacks(int userID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM pack "
                + "WHERE fkUserID = ?";

        ps = connection.prepareStatement(query);
        ps.setInt(1, userID);
        rs = ps.executeQuery();

        LinkedHashMap<Integer, Pack> allUserPacks = new LinkedHashMap();
        while (rs.next()) {
            Pack packs = new Pack();
            User user = new User();
            packs.setPackID(rs.getInt("packID"));
            packs.setPackName(rs.getString("packName"));
            packs.setPackCategoryID(rs.getInt("fkPackCategoryID"));
            packs.setIsPublic(rs.getBoolean("isPublic"));
            user.setUserID(rs.getInt("fkUserID"));
            packs.setUser(user);
            packs.setCreatedDate(rs.getDate("createdDate").toLocalDate());
            
            PackCategory packCategory = new PackCategory();
            
            try {
                    packCategory = PackCategoryDA.selectPackCategory(packs.getPackCategoryID());
                } catch (NamingException | SQLException ex) {
                    
                }
            
            packs.setPackCategoryName(packCategory.getPackCategoryName());

            allUserPacks.put(packs.getPackID(), packs);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return allUserPacks;

    }
    
    public static LinkedHashMap<Integer, Pack> seeAllPublicPacks() throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM pack "
                + "WHERE isPublic = ?";

        ps = connection.prepareStatement(query);
        ps.setBoolean(1, true);
        rs = ps.executeQuery();

        LinkedHashMap<Integer, Pack> allPublicPacks = new LinkedHashMap();
        while (rs.next()) {
            Pack packs = new Pack();
            User user = new User();
            packs.setPackID(rs.getInt("packID"));
            packs.setPackName(rs.getString("packName"));
            packs.setPackCategoryID(rs.getInt("fkPackCategoryID"));
            packs.setIsPublic(rs.getBoolean("isPublic"));
            user.setUserID(rs.getInt("fkUserID"));
            packs.setUser(user);
            packs.setCreatedDate(rs.getDate("createdDate").toLocalDate());
            
            PackCategory packCategory = new PackCategory();
            
            try {
                    packCategory = PackCategoryDA.selectPackCategory(packs.getPackCategoryID());
                } catch (NamingException | SQLException ex) {
                    
                }
            
            packs.setPackCategoryName(packCategory.getPackCategoryName());

            allPublicPacks.put(packs.getPackID(), packs);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return allPublicPacks;

    }
    
    public static Pack selectPack(int packID) throws NamingException, SQLException {
     
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM pack "
                + "WHERE packID = ?";

        ps = connection.prepareStatement(query);
        ps.setInt(1, packID);
        rs = ps.executeQuery();
        
        Pack pack = new Pack();
        User user = new User();
        
        if (rs.next()) {
            pack.setPackID(rs.getInt("packID"));
            pack.setPackName(rs.getString("packName"));
            pack.setPackCategoryID(rs.getInt("fkPackCategoryID"));
            pack.setIsPublic(rs.getBoolean("isPublic"));
            user.setUserID(rs.getInt("fkUserID"));
            pack.setUser(user);
            pack.setCreatedDate(rs.getDate("createdDate").toLocalDate());
        }
        
        return pack;
    }
    
    public static boolean doesPackNameExists(String packName) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT packName FROM pack "
                + "WHERE packName = ?";

        ps = connection.prepareStatement(query);
        ps.setString(1, packName);
        rs = ps.executeQuery();

        boolean b = rs.next();

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return b;

    }
    
    public static int addPack(Pack pack) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        PackCategory packCategory = PackCategoryDA.selectPackCategory(pack.getPackCategoryID());

        String query
                = "INSERT INTO pack (packName, fkPackCategoryID, packHighScore, packHighScoreTime, createdDate, fkUserID) "
                + "VALUES ( ?, ?, ?, ?, ?, ?)";

        ps = connection.prepareStatement(query);
        ps.setString(1, pack.getPackName());
        ps.setInt(2, packCategory.getPackCategoryID());
        ps.setInt(3, pack.getPackHighScore());
        ps.setString(4, pack.getPackHighScoreTime());
        ps.setDate(5, Date.valueOf(pack.getCreatedDate()));
        ps.setInt(6, pack.getUser().getUserID());

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;

    }
    
    public static int deletePack(int packID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;

        String query = "DELETE from pack "
                + "WHERE packID = ?";

        ps = connection.prepareStatement(query);
        ps.setInt(1, packID);

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;
    }
    
    
}
