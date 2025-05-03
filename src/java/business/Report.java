/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;

/**
 *
 * @author lando
 */
public class Report implements Serializable {
    
    private int reportID;
    private String reportType;
    private boolean reportActive;
    private String reportUserNotes;
    private String reportAdminNotes;
    private int reportCreatedByID;
    private int reportedUserID;
    private int reportedPackID;

    public Report() {
    }

    public Report(int reportID, String reportType, boolean reportActive, String reportUserNotes, String reportAdminNotes, int reportCreatedByID, int reportedUserID, int reportedPackID) {
        this.reportID = reportID;
        this.reportType = reportType;
        this.reportActive = reportActive;
        this.reportUserNotes = reportUserNotes;
        this.reportAdminNotes = reportAdminNotes;
        this.reportCreatedByID = reportCreatedByID;
        this.reportedUserID = reportedUserID;
        this.reportedPackID = reportedPackID;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public boolean isReportActive() {
        return reportActive;
    }

    public void setReportActive(boolean reportActive) {
        this.reportActive = reportActive;
    }

    public String getReportUserNotes() {
        return reportUserNotes;
    }

    public void setReportUserNotes(String reportUserNotes) {
        this.reportUserNotes = reportUserNotes;
    }

    public String getReportAdminNotes() {
        return reportAdminNotes;
    }

    public void setReportAdminNotes(String reportAdminNotes) {
        this.reportAdminNotes = reportAdminNotes;
    }

    public int getReportCreatedByID() {
        return reportCreatedByID;
    }

    public void setReportCreatedByID(int reportCreatedByID) {
        this.reportCreatedByID = reportCreatedByID;
    }

    public int getReportedUserID() {
        return reportedUserID;
    }

    public void setReportedUserID(int reportedUserID) {
        this.reportedUserID = reportedUserID;
    }

    public int getReportedPackID() {
        return reportedPackID;
    }

    public void setReportedPackID(int reportedPackID) {
        this.reportedPackID = reportedPackID;
    }
    
    
    
}
