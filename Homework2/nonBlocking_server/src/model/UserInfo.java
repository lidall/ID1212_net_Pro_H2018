/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Lida
 */
public class UserInfo {
    private static String ipInfo;
    private static String wordtoGuess;
    private static String guessStatus;
    private static int chanceLeft;
    private static int score;
    private static int initialFlag;
    public UserInfo(String ipInfo, String wordtoGuess, String guessStatus, int chanceLeft, int score,int initialFlag) {
        this.ipInfo = ipInfo;
        this.wordtoGuess = wordtoGuess;
        this.guessStatus = guessStatus;
        this.chanceLeft = chanceLeft;
        this.score = score;
        this.initialFlag = initialFlag;
    }


    public static String getIPInfo() {
        return ipInfo;
    }

    public void setIPInfo(String ipInfo) {
        this.ipInfo = ipInfo;
    }

    public static String getWordtoGuess() {
        return wordtoGuess;
    }

    public void setWordtoGuess(String wordtoGuess) {
        this.wordtoGuess = wordtoGuess;
    }

    public static String getGuessStatus() {
        return guessStatus;
    }

    public void setGuessStatus(String guessStatus) {
        this.guessStatus = guessStatus;
    }
    
    public static int getChanceLeft() {
        return chanceLeft;
    }

    public void setChanceLeft(int chanceLeft) {
        this.chanceLeft = chanceLeft;
    }
    
     public static int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public static int getFlag() {
        return initialFlag;
    }

    public void setFlag(int initialFlag) {
        this.initialFlag = initialFlag;
    }
}
