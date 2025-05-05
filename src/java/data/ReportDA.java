/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Report;
import business.User;
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
public class ReportDA {
    
    public static int addReport(Report report) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO report (reportType, reportActive, reportUserNotes, reportCreatedByID, reportedUserID, reportedPackID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        ps = connection.prepareStatement(query);
        ps.setString(1, report.getReportType());
        ps.setBoolean(2, report.isReportActive());
        ps.setString(3, report.getReportUserNotes());
        ps.setInt(4, report.getReportCreatedByID());
        ps.setInt(5, report.getReportedUserID());
        ps.setInt(6, report.getReportedPackID());

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;

    }
    
    public static LinkedHashMap<Integer, Report> seeAllActiveReports(boolean reportActive) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM report "
                + "WHERE reportActive = ?";

        ps = connection.prepareStatement(query);
        ps.setBoolean(1, reportActive);
        rs = ps.executeQuery();

        LinkedHashMap<Integer, Report> allActiveReports = new LinkedHashMap();
        while (rs.next()) {
            Report report = new Report();
            User reportedUser = new User();
            User createdUser = new User();
            
            report.setReportID(rs.getInt("reportID"));
            report.setReportType(rs.getString("reportType"));
            report.setReportActive(rs.getBoolean("reportActive"));
            report.setReportUserNotes(rs.getString("reportUserNotes"));
            report.setReportAdminNotes(rs.getString("reportAdminNotes"));
            report.setReportCreatedByID(rs.getInt("reportCreatedByID"));
            report.setReportedUserID(rs.getInt("reportedUserID"));
            report.setReportedPackID(rs.getInt("reportedPackID"));

            reportedUser = UserDA.getUserFromUserID(report.getReportedUserID());
            createdUser = UserDA.getUserFromUserID(report.getReportCreatedByID());
            
            report.setReportedUsername(reportedUser.getUsername());
            report.setReportCreatedUsername(createdUser.getUsername());
            
            allActiveReports.put(report.getReportID(), report);
        }

        rs.close();
        ps.close();
        pool.freeConnection(connection);

        return allActiveReports;

    }
    
    public static int editReport(int reportID, String adminNotes, Boolean reportActive) throws NamingException, SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps;

        String query = "UPDATE report SET reportAdminNotes = ?, reportActive = ? WHERE reportID = ?;";

        ps = connection.prepareStatement(query);
        ps.setString(1, adminNotes);
        ps.setBoolean(2, reportActive);
        ps.setInt(3, reportID);

        int rows = ps.executeUpdate();
        ps.close();
        pool.freeConnection(connection);
        return rows;
    }
}
