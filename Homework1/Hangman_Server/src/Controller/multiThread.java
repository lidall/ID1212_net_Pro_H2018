/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.wrd_Compare;
import Model.wrd_Gen;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
/**
 *
 * @author Lida
 */
public class multiThread extends Thread {
    Socket socket;
    String ipaddress;//the address of the client
    String viewStr;
    
    
    public String wrapPacket(String str){
      String str_len=String.valueOf(str.length());
      String packet=str_len+"~"+str; //add packet header in front of the actual string
      return packet;       
    }
   
    public multiThread (Socket socket){
		this.socket=socket;
	}
    @Override
    public void run() {        
    try{
        int userScore=0;
        BufferedReader bufin =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
	BufferedWriter bufout =
                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        while(true){
            //System.out.println(userScore);
            String score=String.valueOf(userScore);
            String initial=score;
            String initialPacket=wrapPacket(initial);
            bufout.write(initialPacket);
            bufout.newLine();
            bufout.flush();
            
            String inputPacket = bufin.readLine();
            //System.out.println(inputPacket);
            ipaddress = socket.getInetAddress().getHostAddress();
            String[] inputpacket=inputPacket.split("~");
            if(inputpacket[0].equals(String.valueOf(inputpacket[1].length()))){
                
                String inputLine=inputpacket[1];
                //System.out.println(inputpacket[1]);
                if(inputLine.equals("NO")||inputLine.equals("Quit"))
                    break;
                else if(inputLine.equals("YES")){
                    
                    wrd_Gen newWord=new wrd_Gen();
                    newWord.buildWord();
                    int leftChance=newWord.wordLen;
                    String WordtoGuess=newWord.word;                   
                    String Blank=String.valueOf(newWord.blankWord);
                    viewStr=ipaddress+" need to guess: "+WordtoGuess;
                    View.viewCLI.printCLI(viewStr);
                    String firstStr=String.valueOf(leftChance)+":"+Blank+":"+String.valueOf(userScore);
                    String firstPacket=wrapPacket(firstStr);
                    //System.out.println(firstPacket);
                    bufout.write(firstPacket);
                    bufout.newLine();
                    bufout.flush();// send out the questions
                    int QuitFlag=0;
                    while(true){
                        //System.out.println(":)");
                        String Packet = bufin.readLine();
                        String[] packet=Packet.split("~");
                        if(packet[0].equals(String.valueOf(packet[1].length()))){
                            String input=packet[1]; 
                            if (input.equals("Quit")){
                                QuitFlag=1;
                                break;//enable quit anytime
                            }
                            //System.out.println(input);
                            wrd_Compare compare=new wrd_Compare();
                            compare.compareWord(input, leftChance, WordtoGuess, Blank);
                            Blank=compare.outputGuess;
                            leftChance=compare.lifetime;
                            int successFlag=compare.existFlag;//the compare result
                            if(leftChance==0){
                                if(successFlag==1)//success as input is just the word
                                {
                                    userScore=userScore+1;
                                viewStr=ipaddress+" won this time, the score is: "+String.valueOf(userScore);
                                View.viewCLI.printCLI(viewStr);
                                }
                                
                                else if(successFlag==0)// run out of failure time
                                {
                                    userScore=userScore-1;
                                    viewStr=ipaddress+" fail this time, the score is: "+String.valueOf(userScore);
                                    View.viewCLI.printCLI(viewStr);
                                }
                                String completeStr=WordtoGuess+":"+String.valueOf(userScore)+"$"+String.valueOf(successFlag);
                                String completePacket=wrapPacket(completeStr);
                                bufout.write(completePacket);
                                bufout.newLine();
                                bufout.flush();
                                break;                                                                                               
                            }
                            String feedbackStr= new String();
                            if(Blank.equals(WordtoGuess)){
                                userScore=userScore+1;
                                viewStr=ipaddress+" won this time, the score is: "+String.valueOf(userScore);
                                View.viewCLI.printCLI(viewStr);
                                feedbackStr=WordtoGuess+":"+String.valueOf(userScore)+"$"+String.valueOf(successFlag); 
                                String feedbackPacket=wrapPacket(feedbackStr);
                                bufout.write(feedbackPacket);
                                bufout.newLine();
                                bufout.flush();
                               break;// guesss out the word one letter by one letter
                            }
                           
                                
                            feedbackStr=String.valueOf(leftChance)+":"+Blank+":"+String.valueOf(userScore); //keep guessing 
                            String feedbackPacket=wrapPacket(feedbackStr);
                            bufout.write(feedbackPacket);
                            bufout.newLine();
                            bufout.flush();
                        }            
                    }//考虑在本地显示情况
                    if(QuitFlag==1){
                        
                        break;}                                    
            }
            }
        }
        viewStr=ipaddress+" Quit the game";
        View.viewCLI.printCLI(viewStr);
        socket.close();                                                        
    }catch (IOException e)
		{
		}    
} 
    
    
}
