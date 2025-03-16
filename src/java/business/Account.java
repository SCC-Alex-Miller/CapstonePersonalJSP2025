/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author sm405555
 */
public class Account implements Serializable {

    private int accountID;
    private String accountName;
    private Users user;
    private LocalDateTime createdDate;

    public Account() {
    }

    public Account(int accountID, String accountName, Users user, LocalDateTime createdDate) {
        this.accountID = accountID;
        this.accountName = accountName;
        this.user = user;
        this.createdDate = createdDate;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

}
