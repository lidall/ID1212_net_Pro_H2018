/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman_server;

import Controller.socketConnection;
import Model.file_RD;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import View.viewCLI;

/**
 *
 * @author Lida
 */
public class serverInitial {
    public static String[] wordList;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            wordList = file_RD.readtoList();
            viewCLI.printCLI("Server is ready! :)");
            socketConnection.socketConnectionstatic();
            
        } catch (IOException ex) {
            Logger.getLogger(serverInitial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
