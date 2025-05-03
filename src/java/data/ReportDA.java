/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import business.Report;
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
}
