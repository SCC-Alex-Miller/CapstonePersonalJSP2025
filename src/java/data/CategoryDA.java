/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Category;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import javax.naming.NamingException;

/**
 *
 * @author am528423
 */
public class CategoryDA {
    
    public static LinkedHashMap<Integer, Category> selectAllCategoriesByAccountID (int accountID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM category"
                + " WHERE fkAccountId = ?;";
        
        ps = connection.prepareStatement(query);
        ps.setInt(1, accountID);
        rs = ps.executeQuery();
        
        LinkedHashMap<Integer, Category> categories = new LinkedHashMap();
        while (rs.next()) {
            Category category = new Category();
            category.setCategoryID(rs.getInt("categoryId"));
            category.setCategoryName(rs.getString("categoryName"));
            category.setBudgetAmount(rs.getDouble("budget"));
            category.setCreatedDate(rs.getDate("createdDate").toLocalDate());
            category.setAccountID(accountID);

            categories.put(category.getCategoryID(), category);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return categories;
    }

    public static Category selectCategory(int categoryID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM category WHERE categoryId = ?;";
        ps = connection.prepareStatement(query);
        ps.setInt(1, categoryID);
        rs = ps.executeQuery();

        Category selectedCategory = new Category();
        if (rs.next()) {
            selectedCategory.setCategoryID(rs.getInt("categoryId"));
            selectedCategory.setCategoryName(rs.getString("categoryName"));
            selectedCategory.setBudgetAmount(rs.getDouble("budget"));
            selectedCategory.setCreatedDate(rs.getDate("createdDate").toLocalDate());
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return selectedCategory;
    }

    public static int addCategory(Category category, int accountID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO category (categoryName, budget, createdDate, fkAccountId) VALUES (?, ?, ?, ?);";
        ps = connection.prepareStatement(query);

        ps.setString(1, category.getCategoryName());
        ps.setDouble(2, category.getBudgetAmount());
        ps.setDate(3, Date.valueOf(category.getCreatedDate()));
        ps.setInt(4, accountID);

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;
    }

    public static int deleteCategory(int categoryID) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM category "
                + "WHERE categoryId = ?";
        ps = connection.prepareStatement(query);

        ps.setInt(1, categoryID);

        int rows = ps.executeUpdate();

        ps.close();
        pool.freeConnection(connection);
        return rows;
    }

    public static int editCategory(Category category) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;

        String query = "UPDATE category SET categoryName = ?, budget = ? WHERE categoryId = ?;";

        ps = connection.prepareStatement(query);
        ps.setString(1, category.getCategoryName());
        ps.setDouble(2, category.getBudgetAmount());
        ps.setInt(3, category.getCategoryID());

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;
    }

    public static boolean doesCategoryNameExists(String categoryName) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT categoryName FROM category "
                + "WHERE categoryName = ?";

        ps = connection.prepareStatement(query);
        ps.setString(1, categoryName);
        rs = ps.executeQuery();

        boolean b = rs.next();

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return b;

    }

    public static int seedDefaultCategories(int accountId) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;

        String query = "INSERT INTO category (categoryName, budget, createdDate, fkAccountId)"
                + "VALUES"
                + "('Bills', 1000.00, ?, ?),"
                + "('Entertainment', 1000.00, ?, ?),"
                + "('Food', 1000.00, ?, ?),"
                + "('Transportation', 1000.00, ?, ?),"
                + "('Shopping', 1000.00, ?, ?),"
                + "('Miscellaneous', 1000.00, ?, ?);";

        ps = connection.prepareStatement(query);

        ps.setDate(1, Date.valueOf(LocalDate.now()));
        ps.setInt(2, accountId);
        ps.setDate(3, Date.valueOf(LocalDate.now()));
        ps.setInt(4, accountId);
        ps.setDate(5, Date.valueOf(LocalDate.now()));
        ps.setInt(6, accountId);
        ps.setDate(7, Date.valueOf(LocalDate.now()));
        ps.setInt(8, accountId);
        ps.setDate(9, Date.valueOf(LocalDate.now()));
        ps.setInt(10, accountId);
        ps.setDate(11, Date.valueOf(LocalDate.now()));
        ps.setInt(12, accountId);

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;

    }
}