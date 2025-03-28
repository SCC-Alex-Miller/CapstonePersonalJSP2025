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
    private String packCategoryName;
    private int packHighScore;
    private String packHighScoreTime;
    private LocalDate createdDate;
    private User user;

    public Pack() {
    }

    public Pack(int packID, String packName, String packCategoryName, int packHighScore, String packHighScoreTime, LocalDate createdDate, User user) {
        this.packID = packID;
        this.packName = packName;
        this.packCategoryName = packCategoryName;
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

    public String getPackCategoryName() {
        return packCategoryName;
    }

    public void setPackCategoryName(String packCategoryName) {
        this.packCategoryName = packCategoryName;
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
    
    
}
