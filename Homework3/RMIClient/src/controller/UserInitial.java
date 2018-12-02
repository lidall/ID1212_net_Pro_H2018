/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
/**
 *
 * @author Lida
 */
import startup.RMIClient;
import static startup.RMIClient.URL;
import view.cmdLine;

public class UserInitial {
    /*
        Since the server's ip address is not fixed, the client should enter the
        ip address every time to connect with specific server. Also, the url of 
        the remote host can be prepared to connect with the remote interface.
        */
    public static void userInitial(){
        cmdLine.userInitialCMD();
        URL ="rmi://"+RMIClient.SERVER_IP+":9999/rmi.server.controller.ServiceImpl";
    }
    
}
