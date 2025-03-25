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
public class PackCategory implements Serializable {
    
    private int packCategoryID;
    private String packCategoryName;
    private LocalDate packCategoryCreatedDate;
    private int fkUserID;

    public PackCategory() {
    }

    public PackCategory(int packCategoryID, String packCategoryName, LocalDate packCategoryCreatedDate, int fkUserID) {
        this.packCategoryID = packCategoryID;
        this.packCategoryName = packCategoryName;
        this.packCategoryCreatedDate = packCategoryCreatedDate;
        this.fkUserID = fkUserID;
    }
    
    

    public int getPackCategoryID() {
        return packCategoryID;
    }

    public void setPackCategoryID(int packCategoryID) {
        this.packCategoryID = packCategoryID;
    }

    public String getPackCategoryName() {
        return packCategoryName;
    }

    public void setPackCategoryName(String packCategoryName) {
        this.packCategoryName = packCategoryName;
    }

    public LocalDate getPackCategoryCreatedDate() {
        return packCategoryCreatedDate;
    }

    public void setPackCategoryCreatedDate(LocalDate packCategoryCreatedDate) {
        this.packCategoryCreatedDate = packCategoryCreatedDate;
    }

    public int getFkUserID() {
        return fkUserID;
    }

    public void setFkUserID(int fkUserID) {
        this.fkUserID = fkUserID;
    }
    
    
}
