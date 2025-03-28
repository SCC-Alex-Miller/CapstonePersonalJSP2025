/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Pack;
import business.User;
import java.sql.Connection;
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
    
    public static LinkedHashMap<Integer, Pack> seeAllUserPacks(Pack pack) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM pack "
                + "WHERE fkUserID = ?";

        ps = connection.prepareStatement(query);
        ps.setInt(1, pack.getUser().getUserID());
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
    
    public static int addPack(Pack pack) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;

        String query
                = "INSERT INTO pack (packName, fkUserID, createdDate) "
                + "VALUES ( ?, ?, ?)";

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
}
