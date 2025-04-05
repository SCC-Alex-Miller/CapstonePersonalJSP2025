/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Card;
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
public class CardDA {
    
    public static LinkedHashMap<Integer, Card> selectPackCards(int packID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM card "
                + "WHERE fkPackID = ?";

        ps = connection.prepareStatement(query);
        ps.setInt(1, packID);
        rs = ps.executeQuery();

        LinkedHashMap<Integer, Card> packCards = new LinkedHashMap();
        while (rs.next()) {
            Card card = new Card();
            card.setCardID(rs.getInt("cardID"));
            card.setCardQuestion(rs.getString("cardQuestion"));
            card.setCardAnswer(rs.getString("cardAnswer"));
            card.setCardImage(rs.getString("cardImage"));
            card.setFkPackID(packID);

            packCards.put(card.getCardID(), card);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return packCards;

    }
    
    public static int addCard(Card card) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO card (cardQuestion, cardImage, cardAnswer, fkPackID) "
                + "Values(?,?,?,?)";

        ps = connection.prepareStatement(query);
        ps.setString(1, card.getCardQuestion());
        ps.setString(2, card.getCardImage());
        ps.setString(3, card.getCardAnswer());
        ps.setInt(4, card.getFkPackID());

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;

    }
}
