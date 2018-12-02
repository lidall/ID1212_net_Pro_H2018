/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Serverstartup.RMIServer;
import controller.SystemInitial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import common.ICallback;
import static controller.ServiceImpl.getCTX;

/**
 *
 * @author Lida
 */
public class ReminderThread extends Thread implements Runnable {
    
    String oldUserName;
    String oldFileName;
    ICallback callback;
    boolean opChoice;
    
    public ReminderThread(String oldUserName, String oldFileName, ICallback callback, boolean opChoice){
        this.oldUserName = oldUserName;
        this.oldFileName = oldFileName;
        this.callback = callback;
        this.opChoice = opChoice;
    }
    
    @Override
    public void run(){
        int count = 0;
        String newName = null;
        while (true){
            try{
            Thread.currentThread().sleep(3000);
            }catch(Exception e){

            }
            
            Connection conn = SystemInitial.getConn();
            String sql = "select * from UserInfo";
            PreparedStatement pstmt;
            String publicAll = null;
                try {
                    pstmt = (PreparedStatement)conn.prepareStatement(sql);

                    ResultSet rs = pstmt.executeQuery();
                        while (rs.next())
                        {

                            if(rs.getString(1).equals("publicallpermission"))
                            {
                                publicAll = rs.getString(3);
                            }
                           

                        }
                        
            String pureFileName = getCTX("(" + oldFileName+")", "(", ".");           
            sql = "select * from " + pureFileName;
            pstmt = (PreparedStatement)conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            int ct = 0;
            while (rs.next())
                {
                    newName =  rs.getString(1);
                    ct = ct + 1;
                }
            conn.close();
                        
                if (ct > count){
                    count = ct;
                    callback.callbackReminder(newName, oldFileName, "download");
                }
                if(opChoice){
                if(!publicAll.contains("\""+oldUserName+"\":"+oldFileName)){
                    String userChange = RMIServer.getOldFileName(oldFileName, publicAll);
                    callback.callbackReminder(userChange, oldFileName, "update");
                    break;
                }}

    } catch (Exception e) {
        e.printStackTrace();
    }
        }
        
        
    }
    
}
