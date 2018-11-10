/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Net;



import Controller.functionControl;
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
    public static int QuitFlag = 0;
    
   
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
            
            String initialPacket = functionControl.initiateCall(userScore);
            
            bufout.write(initialPacket);
            bufout.newLine();
            bufout.flush();
            
            String inputPacket = bufin.readLine();
            ipaddress = socket.getInetAddress().getHostAddress()+String.valueOf(socket.getPort());
          
            //String[] inputpacket=inputPacket.split("~");
            //if(inputpacket[0].equals(String.valueOf(inputpacket[1].length()))){
                String inputLine=functionControl.checkPacket(inputPacket,socket);
                if(inputLine.equals("NO")||inputLine.equals("Quit"))
                    break;
                else if(inputLine.equals("YES")){
                    
                    String WordtoGuess = null;
                    String Blank = null;
                    int leftChance = 0;
                    String firstFeedback = functionControl.firstCall(ipaddress, userScore);
                    
                    leftChance= Integer.parseInt(functionControl.splitCall(firstFeedback, "@", "#"));
                    WordtoGuess=functionControl.splitCall(firstFeedback, "#", "-");
                    Blank=functionControl.splitCall(firstFeedback, "-", "%");
                    //System.out.println(Blank);
                    String[] firstPacket = firstFeedback.split("@");
                    bufout.write(firstPacket[0]);
                    bufout.newLine();
                    bufout.flush();// send out the questions
                    
                    while(true){
                    int scoreFlag=0;   
                    String Packet = bufin.readLine();
                    String compareFeedback = functionControl.mainCall(socket,Packet, userScore, ipaddress,leftChance,WordtoGuess,Blank);
                    String[] sendStr=compareFeedback.split("@");
                    leftChance= Integer.parseInt(functionControl.splitCall(compareFeedback, "@", "#"));
                    WordtoGuess=functionControl.splitCall(compareFeedback, "#", "(");
                    Blank=functionControl.splitCall(compareFeedback, "(", "%");
                    //System.out.println(sendStr[0]);
                    userScore=Integer.parseInt(functionControl.splitCall(compareFeedback, "%", "&"));
                    scoreFlag= Integer.parseInt(functionControl.splitCall(compareFeedback, "&", "^"));
                    //System.out.println(sendStr[0]);
                    bufout.write(sendStr[0]);
                    bufout.newLine();
                    bufout.flush();
                    if(scoreFlag==1){
                        break;
                    }
                    
                    if(QuitFlag==1){
                        break;
                    }
                    
                    }
                    if(QuitFlag==1){
                        break;
                    }                                    
            }
            //}
        }
        String viewStr=ipaddress+" Quit the game";
        functionControl.viewCall(viewStr);
        socket.close();                                                        
    }catch (IOException e)
		{
		}    
} 
    
    
}
