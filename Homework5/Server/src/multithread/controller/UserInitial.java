/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multithread.controller;

import multithread.model.DataProcessing;
import multithread.net.Multithread;
import static multithread.net.Multithread.USER_BASE;
import static multithread.net.Multithread.getCTX;

/**
 *
 * @author Lida
 */
public class UserInitial {
    static boolean USER_FLG;
    public static String userInitial(String rcvIP){
        String sendCTX = null;
        USER_FLG = true;
                    for(int i=0;i<Multithread.USER_BASE.size();i++)
                    {
                        String temp = Multithread.USER_BASE.get(i);
                        String baseIP = Multithread.getCTX(temp,"/",")");
                        /*
                        If a client stops the program by mistake and the game is not correctly
                        closed, the user data is still stored in USER_BASE. If the client connect
                        with the server again, the game will continue from where it stopped.
                        The USER_FLG will be set false in order to skip the initialization of
                        the game.
                        */
                        if(rcvIP.equals(baseIP))
                        {
                            String userAttempt = Multithread.getCTX(temp,"<",">");
                            String userUnderline = Multithread.getCTX(temp,"[","]");
                            String userWord = Multithread.getCTX(temp,"{","}");
                            int wordLength = userWord.length();
                            sendCTX = "/"+baseIP+")"+"{on}"+"["+"Game continue: "+
                                    userUnderline+" <"+wordLength+" letters>"+" Please guess a letter:"
                                    + "  You can quit the game just type \"QUIT\"]";
                             //sending welcome message to the client
                            sendCTX = null;
                            USER_FLG = false;
                            System.out.println(USER_BASE);
                            break;
                        }
                    }
            
            if(USER_FLG){
                //Randomly picking up a word from the library, and replace the word
                //with underline for display. 
                DataProcessing.initial();
                int wordLength = DataProcessing.GUESS_WORD.length();
                System.out.println("Guessing word:"+DataProcessing.GUESS_WORD+" "+wordLength+" letters");

                //Encapsulating the welcome message that will be sent to the client
                sendCTX = "/"+rcvIP+")"+"{on}"+"["+"Game starts!"+
                        ":"+multithread.model.DataProcessing.SEND_UNDERLINE+" <"+
                        wordLength+" letters>"+" Please guess a letter:  "
                        + "You can quit the game just type \"QUIT\"]";

                //Storing the user data into USER_BASE
                String userIP = "/"+rcvIP+")";
                userIP = getCTX(userIP,"/",")");
                int userAttempt = wordLength;
                String userData = "/"+userIP+")"+"<"+userAttempt+">"+"!0?"+"["+
                        DataProcessing.SEND_UNDERLINE+"]"+"{"+DataProcessing.GUESS_WORD+"}";
                USER_BASE.add(userData);
                System.out.println(USER_BASE);
                
            }
            return sendCTX;
    }
}
