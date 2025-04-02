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
            user.setUserID(rs.getInt("fkUserID"));
            packs.setUser(user);
            packs.setCreatedDate(rs.getDate("createdDate").toLocalDate());

            allUserPacks.put(packs.getPackID(), packs);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return allUserPacks;

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
}
