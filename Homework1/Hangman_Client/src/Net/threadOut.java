/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Net;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author Lida
 */
public class threadOut extends Thread{
    Socket socket;
	public threadOut(Socket socket){
		this.socket=socket;
	}
	@Override
        public void run(){
		try{
                    BufferedWriter socketout=
			 new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    while(true){
                    String tapIn=view.viewCLI.tapIn();
                    if((tapIn)!=null){
			if((tapIn).equals("NO")||(tapIn).equals("Quit")){
			break;
			}
                        socketout.write(controller.functionControl.wrapPckt(tapIn));
			socketout.newLine();
			socketout.flush();
                     }
                }
                    socket.close();
                    
        }
                catch (IOException e)
                {
                }
     }
    
}
