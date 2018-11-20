/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
    public static String[] formStr(String item){
        String[] strElement;
        String[] viewStr = new String[2];
        int elementNum;
        strElement=item.split(":");
        elementNum=strElement.length;
        if(elementNum==1){
            viewStr[0]="Welcome to our game! Your Score is "+strElement[0]+
                    ". Do you want to START? ( YES/NO )"; 
            viewStr[1]=null;
            
        }
        else if(elementNum==2){
            int successFlag;
            String temp = strElement[1];
            String[] successElement=temp.split("\\$");
            successFlag=Integer.parseInt(successElement[1]);
            if (successFlag==1){
                viewStr[0]="Congratulations! The word is: "+strElement[0]+
                        ". Your Score is: "+successElement[0];
                viewStr[1]="Welcome to our game! Your Score is "+successElement[0]+
                    ". Do you want to START? ( YES/NO )";  
            }
            else if(successFlag==0){
                viewStr[0]="Run out your Chances! The word is: "+strElement[0]+
                        " Your Score is: "+successElement[0];
                viewStr[1]="Welcome to our game! Your Score is "+successElement[0]+
                    ". Do you want to START? ( YES/NO )";  
            }     
         }
        else if(elementNum==3){
          viewStr[0]="Your left chances are: "+strElement[0]+": You have guessed out:  " +strElement[1]
                  +"    Your Score is: "+strElement[2];
          viewStr[1]=null;
        }
    return viewStr;
}
    
}
