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
public class StudySession implements Serializable {
    
    private int sessionID;
    private int sessionRight;
    private int sessionWrong;
    private String sessionTime;

    public StudySession() {
    }

    public StudySession(int sessionID, int sessionRight, int sessionWrong, String sessionTime) {
        this.sessionID = sessionID;
        this.sessionRight = sessionRight;
        this.sessionWrong = sessionWrong;
        this.sessionTime = sessionTime;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getSessionRight() {
        return sessionRight;
    }

    public void setSessionRight(int sessionRight) {
        this.sessionRight = sessionRight;
    }

    public int getSessionWrong() {
        return sessionWrong;
    }

    public void setSessionWrong(int sessionWrong) {
        this.sessionWrong = sessionWrong;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }
    
    
}
