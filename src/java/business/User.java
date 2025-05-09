/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author lando
 */
public class User implements Serializable {
    
    private int userID;
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;
    private int reportStrikes;
    private boolean activeStatus;
    private String adminMessage;
    private LocalDate createdDate;
    
    public User(){}
    
    public User(int userID, String username, String email, String password, boolean isAdmin, int reportStrikes, boolean activeStatus, String adminMessage, LocalDate createdDate){
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.reportStrikes = reportStrikes;
        this.activeStatus = activeStatus;
        this.adminMessage = adminMessage;
        this.createdDate = createdDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getAdminMessage() {
        return adminMessage;
    }

    public void setAdminMessage(String adminMessage) {
        this.adminMessage = adminMessage;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public int getReportStrikes() {
        return reportStrikes;
    }

    public void setReportStrikes(int reportStrikes) {
        this.reportStrikes = reportStrikes;
    }
    
    
}
