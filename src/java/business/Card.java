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
public class Card implements Serializable {
    
    private int cardID;
    private String cardQuestion;
    private String cardAnswer;
    private String cardImage;
    private int fkPackID;

    public Card() {
    }

    public Card(int cardID, String cardQuestion, String cardAnswer, String cardImage, int fkPackID) {
        this.cardID = cardID;
        this.cardQuestion = cardQuestion;
        this.cardAnswer = cardAnswer;
        this.cardImage = cardImage;
        this.fkPackID = fkPackID;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public String getCardQuestion() {
        return cardQuestion;
    }

    public void setCardQuestion(String cardQuestion) {
        this.cardQuestion = cardQuestion;
    }

    public String getCardAnswer() {
        return cardAnswer;
    }

    public void setCardAnswer(String cardAnswer) {
        this.cardAnswer = cardAnswer;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public int getFkPackID() {
        return fkPackID;
    }

    public void setFkPackID(int fkPackID) {
        this.fkPackID = fkPackID;
    }
    
    
}
