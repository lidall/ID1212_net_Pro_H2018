/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multithread.controller;

import multithread.model.DataProcessing;
import multithread.model.Database;
import multithread.net.Multithread;

/**
 *
 * @author Lida
 */
public class UserProcess {
    public static String userProcess(String rcvCTX){
         String continueFLG = Multithread.getCTX(rcvCTX,"[","]");
        String sendCTX = null;
        switch (continueFLG) {
            case "YES":
                //If the client wants to continue playing the game (the content of 
                //the message is "YES"), the server will initialize the game again
                //and start a new round.
                {
                    String rcvIP = Multithread.getCTX(rcvCTX,"/",")");
                    for(int i=0;i<Multithread.USER_BASE.size();i++)
                    {

                        String temp = Multithread.USER_BASE.get(i);
                        String baseIP = Multithread.getCTX(temp,"/",")");

                        if(rcvIP.equals(baseIP))
                        {
                            String userRecord = Multithread.getCTX(temp,"!","?");
                            DataProcessing.initial();
                            int wordLength=DataProcessing.GUESS_WORD.length();
                            int userAttempt = wordLength;
                            String userWord=DataProcessing.GUESS_WORD;
                            String userUnderline = DataProcessing.SEND_UNDERLINE;
                            Multithread.USER_BASE.remove(i);
                            String userData;
                            userData = "/"+baseIP+")"+"<"+userAttempt+">"+"!"+userRecord+
                                    "?"+"["+userUnderline+"]"+"{"+userWord+"}";
                            Multithread.USER_BASE.add(userData);
                            System.out.println(Multithread.USER_BASE);
                            sendCTX = null;
                            sendCTX ="/"+baseIP+")"+"{on}"+"["+"New round: Please guess a letter"+
                                    userUnderline+" <"+wordLength+" letters>"+"]";
                            break;
                        }

                    }
                    break;
                }
            case "NO":
                //If the client wants to exit the game (the content of the message 
                //is "NO", the server will send a goodbye message which contains 
                //the score to the client and delete all the user data.
                {
                    String rcvIP = Multithread.getCTX(rcvCTX,"/",")");
                    for(int i=0;i<Multithread.USER_BASE.size();i++)
                    {

                        String temp = Multithread.USER_BASE.get(i);
                        String baseIP = Multithread.getCTX(temp,"/",")");

                        if(rcvIP.equals(baseIP))
                        {
                            String userRecord = Multithread.getCTX(temp,"!","?");
                            Multithread.USER_BASE.remove(i);
                            sendCTX = null;
                            sendCTX ="/"+baseIP+")"+"{end}"+"[" +"Thanks for playing"+
                                    "!"+" You have "+userRecord+" points!"+"]";
                            break;
                        }

                    }       
                    break;
                }
            case "QUIT":
                //If the client wants to temporarily exit the game (the content of the message 
                //is "QUIT", the server will send a goodbye message which contains 
                //the score to the client and delete the user data.
                {
                    String rcvIP = Multithread.getCTX(rcvCTX,"/",")");
                    for(int i=0;i<Multithread.USER_BASE.size();i++)
                    {

                        String temp = Multithread.USER_BASE.get(i);
                        String baseIP = Multithread.getCTX(temp,"/",")");

                        if(rcvIP.equals(baseIP))
                        {
                            Multithread.USER_BASE.remove(i);
                            String userRecord = Multithread.getCTX(temp,"!","?");
                            sendCTX = null;
                            sendCTX ="/"+baseIP+")"+"{end}"+"[" +"Thanks for playing"+
                                    "!"+" You have "+userRecord+" points!"+"]";
                            break;
                        }

                    }       
                    break;
                }
            default:
                sendCTX = Database.dataProcess(rcvCTX);
                break;
        }
        return sendCTX;
    }
}
