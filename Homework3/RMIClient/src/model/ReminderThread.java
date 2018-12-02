/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import common.ICallback;
import java.rmi.server.UnicastRemoteObject;
import startup.RMIClient;
import common.RMIInterface;
import java.rmi.Naming;
import static startup.RMIClient.URL;
/**
 *
 * @author Lida
 */

public class ReminderThread extends Thread implements Runnable {
    
    String UserName;
    String FileName;
    ICallback callback;
    boolean opChoice;
    
    public ReminderThread(ICallback callback, String UserName, String FileName, boolean opChoice){
        this.UserName = UserName;
        this.FileName = FileName;
        this.callback = callback;
        this.opChoice = opChoice;
    }
    
    @Override
    public void run(){
        try{
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL);
        userOperation.callReminder(callback, RMIClient.USER_NAME, FileName, opChoice);
        
        }catch(Exception e){
            
        }
        
        }
        
        
    
    
}
