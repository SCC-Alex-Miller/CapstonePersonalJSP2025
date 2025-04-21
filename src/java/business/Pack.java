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
public class Pack implements Serializable {
    
    private int packID;
    private String packName;
    private int packCategoryID;
    private String packCategoryName;
    private boolean isPublic;
    private int packHighScore;
    private String packHighScoreTime;
    private LocalDate createdDate;
    private User user;

    public Pack() {
    }

    public Pack(int packID, String packName, int packCategoryID, String packCategoryName, boolean isPublic, int packHighScore, String packHighScoreTime, LocalDate createdDate, User user) {
        this.packID = packID;
        this.packName = packName;
        this.packCategoryID = packCategoryID;
        this.packCategoryName = packCategoryName;
        this.isPublic = isPublic;
        this.packHighScore = packHighScore;
        this.packHighScoreTime = packHighScoreTime;
        this.createdDate = createdDate;
        this.user = user;
    }

    

    public int getPackID() {
        return packID;
    }

    public void setPackID(int packID) {
        this.packID = packID;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public int getPackCategoryID() {
        return packCategoryID;
    }

    public void setPackCategoryID(int packCategoryID) {
        this.packCategoryID = packCategoryID;
    }

    public int getPackHighScore() {
        return packHighScore;
    }

    public void setPackHighScore(int packHighScore) {
        this.packHighScore = packHighScore;
    }

    public String getPackHighScoreTime() {
        return packHighScoreTime;
    }

    public void setPackHighScoreTime(String packHighScoreTime) {
        this.packHighScoreTime = packHighScoreTime;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPackCategoryName() {
        return packCategoryName;
    }

    public void setPackCategoryName(String packCategoryName) {
        this.packCategoryName = packCategoryName;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
    
    
}
