/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Expense;
import java.sql.*;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author kj418824
 */
public class ExpenseDA {
    
    
    public static int insert(Expense expense) throws NamingException, SQLException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query
                = "INSERT INTO expense (expenseName, amount, transactionDate, frequency, fkCategoryId, fkAccountId) "
                + "Values(?,?,?,?,?,?)";
        
        ps = connection.prepareStatement(query);
        ps.setString(1, expense.getExpenseName());
        ps.setDouble(2, expense.getAmount());
        ps.setDate(3, Date.valueOf(expense.getTransactionDate()));
        ps.setInt(4, expense.getFrequency());
        ps.setInt(5, expense.getFkCategoryID());
        ps.setInt(6, expense.getFkAccountID());
        
        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;
        
    }
    
    public static LinkedHashMap<Integer, Expense> getExpenseList(int fkAccountId) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM expense WHERE fkAccountId = ?";

        ps = connection.prepareStatement(query);
        ps.setInt(1, fkAccountId);
        rs = ps.executeQuery();

        LinkedHashMap<Integer, Expense> expenseList = new LinkedHashMap();
        while (rs.next()) {
            Expense expense = new Expense();
            expense.setExpenseID(rs.getInt("expenseId"));
            expense.setExpenseName(rs.getString("expenseName"));
            expense.setAmount(rs.getDouble("amount"));
            expense.setTransactionDate(rs.getDate("transactionDate").toLocalDate());
            expense.setFrequency(rs.getInt("frequency"));
            expense.setFkCategoryID(rs.getInt("fkCategoryId"));
            expense.setFkAccountID(rs.getInt("fkAccountId"));
            
            expenseList.put(expense.getExpenseID(), expense);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return expenseList;

    }
    
    public static int delete(int expenseID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        
        String query = "DELETE FROM expense "
                + "WHERE expenseId = ?";
        
        ps = connection.prepareStatement(query);
        ps.setInt(1, expenseID);
        int row = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return row;
    }
    
    public static int update(Expense expense) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "UPDATE expense SET "
                        + "expenseName = ?,"
                        + "amount = ?, "
                        + "transactionDate = ?, "
                        + "frequency = ?, "
                        + "fkCategoryId = ?, "
                        + "WHERE expenseId = ?";
        
        ps = connection.prepareStatement(query);
        ps.setString(1, expense.getExpenseName());
        ps.setDouble(2, expense.getAmount());
        ps.setDate(3, Date.valueOf(expense.getTransactionDate()));
        ps.setInt(4, expense.getFrequency());
        ps.setInt(5, expense.getFkCategoryID());
        ps.setInt(6, expense.getFkAccountID());
        
        int row = ps.executeUpdate();
        
        ps.close();
        pool.freeConnection(connection);
        return row;
    }
}
