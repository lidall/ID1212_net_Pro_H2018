/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import net.ServerConnector;

/**
 *
 * @author Lida
 */
public class Controller {
    private ServerConnector connection =new ServerConnector();
    public void connect(String IP) {
        connection.connectToServer(IP);
    }
    public void disconnect() throws IOException {
        connection.leaveServer();
    }
    public void messageAppend(String message) {
        connection.addUserMsgToQueue(message);
    }
    public static void Interface(String input){
        String[] viewStr=view.formStr.formStr(input);
        for(int i=0;i<2;i++){
          if(!(viewStr[i] == null)) {
              view.viewCLI.viewInter(viewStr[i]);
          }
        }
        
    }
}
