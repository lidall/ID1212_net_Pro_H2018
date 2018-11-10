/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Lida
 */
public class formStr {
    public static int packetFlag=0;
    public static String checkPacket(String item,Socket socket) throws IOException{
        BufferedReader bufin =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
    String receivedMessage=null;
    
    String[] recPacket=item.split("~");
    if(recPacket[0].equals(String.valueOf(recPacket[1].length()))){
        receivedMessage=recPacket[1];
    }
    else{
        while(true){
        String inputPacket = bufin.readLine();
        recPacket[1]=recPacket[1]+inputPacket;
        if(recPacket[0].equals(String.valueOf(recPacket[1].length()))){
        receivedMessage=recPacket[1];
        break;
            }
        }
    }
    
    
    return receivedMessage; 
    }
    public static String formStr(String item){
        String[] strElement;
        String viewStr = null;
        int elementNum;
        strElement=item.split(":");
        elementNum=strElement.length;
        if(elementNum==1){
            viewStr="Welcome to our game! Your Score is "+strElement[0]+
                    ". Do you want to START? ( YES/NO )";  
            
        }
        else if(elementNum==2){
            int successFlag;
            String temp = strElement[1];
            String[] successElement=temp.split("\\$");
            successFlag=Integer.parseInt(successElement[1]);
            if (successFlag==1){
                viewStr="Congratulations! The word is: "+strElement[0]+
                        ". Your Score is: "+successElement[0];
            }
            else if(successFlag==0){
                viewStr="Run out your Chances! The word is: "+strElement[0]+
                        " Your Score is: "+successElement[0];
            }     
         }
        else if(elementNum==3){
          viewStr="Your left chances are: "+strElement[0]+": You have guessed out:  " +strElement[1]
                  +"    Your Score is: "+strElement[2];
        }
    return viewStr;
}
    public static String wrapPacket(String str){
      String str_len=String.valueOf(str.length());
      String packet=str_len+"~"+str; //add packet header in front of the actual string
      return packet;       
    }
}
