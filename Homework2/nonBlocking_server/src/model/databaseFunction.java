/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Lida
 */
public class databaseFunction {
    
    public static int dataInsert(UserInfo userInfo) {
    Connection conn = sqlConnector.sqlconnector();
    int i = 0;
    String sql = "insert into client (user,wordtoGuess,guessBlank,leftChance,score,initialFlag) values(?,?,?,?,?,?)";
    PreparedStatement pstmt;
    try {
        
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        pstmt.setString(1, userInfo.getIPInfo());
        pstmt.setString(2, userInfo.getWordtoGuess());
        pstmt.setString(3, userInfo.getGuessStatus());
        pstmt.setInt(4, userInfo.getChanceLeft());
        pstmt.setInt(5, userInfo.getScore());
        pstmt.setInt(6, userInfo.getFlag());
        i = pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    public static int blankUpdate(UserInfo userInfo) {
    Connection conn = sqlConnector.sqlconnector();
    int i = 0;
    String sql = "update client set guessBlank='" + userInfo.getGuessStatus() + "' where user='" + userInfo.getIPInfo() + "'";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        i = pstmt.executeUpdate();
     //   System.out.println("resutl: " + i);
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    public static int chanceUpdate(UserInfo userInfo) {
    Connection conn = sqlConnector.sqlconnector();
    int i = 0;
    String sql = "update client set leftChance='" + userInfo.getChanceLeft() + "' where user='" + userInfo.getIPInfo() + "'";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        i = pstmt.executeUpdate();
     //   System.out.println("resutl: " + i);
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    public static int scoreUpdate(UserInfo userInfo) {
    Connection conn = sqlConnector.sqlconnector();
    int i = 0;
    String sql = "update client set score='" + userInfo.getScore() + "' where user='" + userInfo.getIPInfo() + "'";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        i = pstmt.executeUpdate();
     //   System.out.println("resutl: " + i);
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    public static int flagUpdate(UserInfo userInfo) {
    Connection conn = sqlConnector.sqlconnector();
    int i = 0;
    String sql = "update client set initialFlag='" + userInfo.getFlag() + "' where user='" + userInfo.getIPInfo() + "'";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        i = pstmt.executeUpdate();
     //   System.out.println("resutl: " + i);
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    public static int GuessUpdate(UserInfo userInfo) {
    Connection conn = sqlConnector.sqlconnector();
    int i = 0;
    String sql = "update client set guessBlank='" + userInfo.getGuessStatus()
            +"'," +"wordtoGuess='"+userInfo.getWordtoGuess()
            +"',leftChance='"+userInfo.getChanceLeft()
            +"',initialFlag='"+userInfo.getFlag()
            + "' where user='" + userInfo.getIPInfo() + "'";
    //System.out.println(sql);
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        i = pstmt.executeUpdate();
     //   System.out.println("resutl: " + i);
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    public static UserInfo clientQuery(String ID) throws SQLException{
        Connection conn = sqlConnector.sqlconnector();
        int i = 0;
        String sql = "select * from client";
        PreparedStatement pstmt;
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            String wordtoguess=null;
            String guessedblank=null;
            int leftChance=0;
            int score=0;
            int intialFlag=1;
            while (rs.next())
            //if the ResultSet has data, check whether there is a match in the ResultSet
            //If there is a match, it means the user exists.
                {                   
                    if(rs.getString(1).equals(ID))
                    {
                        wordtoguess=rs.getString(2);
                        guessedblank=rs.getString(3);
                        leftChance=rs.getInt(4);
                        score=rs.getInt(5);
                        intialFlag=rs.getInt(6);
                        break;
                    }
                    
                }
           UserInfo user=new UserInfo(ID,wordtoguess,guessedblank,leftChance,score,intialFlag);

   
       return user;
        
    }
    public static int dataDelete(UserInfo userInfo) {
    String userName = userInfo.getIPInfo();
    Connection conn = sqlConnector.sqlconnector();
    int i = 0;
    String sql = "delete from client where user='" + userName + "'";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);//create a PreparedStatement object to delete the content in the database
        i = pstmt.executeUpdate(); //get the number of updated paremeters
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    
}
