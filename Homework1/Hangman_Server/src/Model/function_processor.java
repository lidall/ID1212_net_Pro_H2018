/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Controller.functionControl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Lida
 */
public class function_processor {
    
    //public static String WordtoGuess;
    //public static String Blank;
    //public static int leftChance;
    
    public static String wrapPacket(String str){
      String str_len=String.valueOf(str.length());
      String packet=str_len+"~"+str; //add packet header in front of the actual string
      return packet;       
    }



    public String initiateClient(int userScore){
    String score=String.valueOf(userScore);
            String initial=score;
            String initialPacket=wrapPacket(initial);
            return initialPacket;
            
    }
    
    public String wordGen(String ipaddress, int userScore) throws IOException{
        wrd_Gen newWord=new wrd_Gen();
        newWord.buildWord();
        int leftChance=newWord.wordLen;
        String WordtoGuess=newWord.word;                   
        String Blank=String.valueOf(newWord.blankWord);
        String viewStr=ipaddress+" need to guess: "+WordtoGuess;
        functionControl.viewCall(viewStr);
        String firstStr=String.valueOf(leftChance)+":"+Blank+":"+String.valueOf(userScore);
        String firstPacket=wrapPacket(firstStr);
        String firstFeedback=firstPacket+"@"+String.valueOf(leftChance)+"#"+WordtoGuess+"-"+Blank+"%";
        //System.out.println(firstFeedback);
        return firstFeedback;
    }
    
public static String functionChoice(Socket socket,String Packet, int userScore, String ipaddress,int leftChance,String WordtoGuess ,String Blank) throws IOException{
    String sendstr = null;
    String sendFeedback=null;
        //String[] packet=Packet.split("~");
        //if(packet[0].equals(String.valueOf(packet[1].length()))){
            String input=checkPckt(Packet,socket); 
            if (input.equals("Quit")){
                Net.multiThread.QuitFlag=1;
            }
            //System.out.println(":)");
            wrd_Compare compare=new wrd_Compare();
            compare.compareWord(input, leftChance, WordtoGuess, Blank);
            Blank=compare.outputGuess;
            //System.out.println(Blank);
            leftChance=compare.lifetime;
            int successFlag=compare.existFlag;//the compare result
            
            String feedbackStr;
            int scoreFlag=0;
            
            if(Blank.equals(WordtoGuess)){
                scoreFlag=1;
                userScore=userScore+1;
                String viewStr=ipaddress+" won this time, the score is: "+String.valueOf(userScore);
                functionControl.viewCall(viewStr);
                feedbackStr=WordtoGuess+":"+String.valueOf(userScore)+"$"+String.valueOf(successFlag); 
                sendstr=wrapPacket(feedbackStr);

            }
            else{
                if(leftChance==0){
                    scoreFlag=1;
                    userScore=userScore-1;
                    String viewStr=ipaddress+" fail this time, the score is: "+String.valueOf(userScore);
                    functionControl.viewCall(viewStr);
                    feedbackStr=WordtoGuess+":"+String.valueOf(userScore)+"$"+String.valueOf(successFlag);
                    sendstr=wrapPacket(feedbackStr);
                }
                else{
                    feedbackStr=String.valueOf(leftChance)+":"+Blank+":"+String.valueOf(userScore); //keep guessing 
                    sendstr=wrapPacket(feedbackStr);
                }
            } 
            //System.out.println(Blank);
          sendFeedback=sendstr+"@"+String.valueOf(leftChance)+"#"+WordtoGuess+"("+Blank+"%"+String.valueOf(userScore)
                  +"&"+String.valueOf(scoreFlag)+"^";
            
        //}            
    
    return sendFeedback;//Sender need to add userScore too
}

public static String getCTX(String originalCTX,String firstSplit,String secondSplit)
    {
        String resultCTX = originalCTX.substring(originalCTX.lastIndexOf(firstSplit), 
                originalCTX.lastIndexOf(secondSplit));
        resultCTX = resultCTX.substring(1,resultCTX.length());
        return resultCTX;
    }
public static String checkPckt(String receivedPacket, Socket socket) throws IOException{
    BufferedReader bufin =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
    String receivedMessage=null;
    
    String[] recPacket=receivedPacket.split("~");
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


}
