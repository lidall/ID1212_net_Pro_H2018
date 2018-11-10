/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lida
 */
public class clientConnection {
    public static void clientConnection(){
        try {
            view.viewCLI.printCLI("Input server IP address: ");
            @SuppressWarnings("resource")
                    String serverIP=view.viewCLI.tapIn();
                    Socket socket=new Socket(serverIP,9999);
                    new Net.threadIN(socket).start();
                    new Net.threadOut(socket).start();
                    
        } catch (IOException ex) {
            Logger.getLogger(clientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        
    }
    

