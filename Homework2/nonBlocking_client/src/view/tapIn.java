/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import java.io.IOException;
import java.util.Scanner;
import controller.Controller;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Lida
 */
public class tapIn implements Runnable{
    private Controller cont;
    private String[] msgArray;

    public void start() {
        System.out.println("Tap in the server's IP:");
        cont = new Controller();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(true){
            Scanner userEntry = new Scanner(System.in);
            String userMsg = userEntry.nextLine();
            //System.out.println(userMsg);
            String[] IPsplit;
            IPsplit = userMsg.split("\\.");
            //System.out.println(IPsplit[1]);
            if(IPsplit.length>1)
                {
                    cont.connect(userMsg);
                }
            else if(userMsg.equals("Quit")||userMsg.equals("NO")){
                try {
                    cont.disconnect();
                    System.exit(0);
                } catch (IOException ex) {
                    Logger.getLogger(tapIn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                cont.messageAppend(userMsg);
            }
        }
        
        
        
    }
    
    
}
