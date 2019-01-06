/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.UserChoice;
import static startup.SystemClient.SERVER_IP;
import view.LogInterface;
import view.RegisterInterface;
import view.SetServerAddr;

/**
 *
 * @author Lida
 */


public class UserProcess {
    
    public static void userSetServer(){
        UserInitial.userInitial();
        SetServerAddr.jf.setVisible(false);
        LogInterface.logMain();
        System.out.println("Server ip = " + SERVER_IP);
    }
    
    public static boolean userLoginProcess(String sendCTX) throws Exception{
        
        boolean returnFLG = UserChoice.userLogin(sendCTX);
        return returnFLG;
    }
    
    public static boolean userRegisterProcess(String sendCTX) throws Exception{
        
        boolean returnFLG = UserChoice.userRegister(sendCTX);
        return returnFLG;
    }
    
    public static void registerInterface(){
        RegisterInterface.registerMain();
    }
    
}
