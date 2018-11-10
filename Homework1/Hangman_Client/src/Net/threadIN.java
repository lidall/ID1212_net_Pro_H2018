/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Net;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import model.formStr;

/**
 *
 * @author Lida
 */
public class threadIN extends Thread {
    Socket socket;
    public String strIn;
    public threadIN(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run(){
        try{
        BufferedReader socketin=
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
        strIn=null;
        while((strIn=socketin.readLine())!=null){
            //System.out.println(strIn)
            String dataIn=controller.functionControl.checkPckt(strIn, socket);
            String viewStr=controller.functionControl.formString(dataIn);
            view.viewCLI.printCLI(viewStr);
            
        }
        }catch (IOException e)
		{
		} 
    
}
    
    
    
    
    
}
