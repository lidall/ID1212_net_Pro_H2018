/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Lida
 */
public class socketConnection {
    public static void socketConnectionstatic() throws IOException{
	@SuppressWarnings("resource")
	ServerSocket server = new ServerSocket(9999);
	while(true){
		Socket socket=server.accept();
		String hostname=socket.getInetAddress().getHostAddress();
		View.viewCLI.printCLI(hostname+" is connceting with the server now");
		new Net.multiThread(socket).start();
        }
        
    
}
    
}
