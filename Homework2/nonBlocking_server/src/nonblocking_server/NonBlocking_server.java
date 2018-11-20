/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nonblocking_server;

import java.io.IOException;
import model.file_RD;
import view.viewCLI;
import net.clientConnection;
/**
 *
 * @author Lida
 */
public class NonBlocking_server {
    public static String[] wordList;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        wordList = file_RD.readtoList();
        viewCLI.printCLI("Server is ready! :)");
        clientConnection server=new clientConnection();
        server.start();
    }
    
}
