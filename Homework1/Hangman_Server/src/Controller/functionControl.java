package Controller;

import Model.function_processor;
import View.viewCLI;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Lida
 */
public class functionControl {

    public static String initiateCall(int userScore){
        function_processor process = new function_processor();
        String initialPacket=process.initiateClient(userScore);
        
        return initialPacket;
    }
    
    public static String firstCall(String ipaddress, int userScore) throws IOException{
        function_processor process = new function_processor();
        String firstPacket = process.wordGen(ipaddress, userScore);
        return firstPacket;
    }
    
    public static String mainCall(Socket socket,String Packet, int userScore, String ipaddress,int leftChance,String WordtoGuess ,String Blank) throws IOException{
        String sendStr = Model.function_processor.functionChoice(socket,Packet, userScore, ipaddress, leftChance, WordtoGuess, Blank);
        return sendStr;
    }
    
    public static void viewCall(String viewStr){
        viewCLI.printCLI(viewStr);
    }
    public static String splitCall(String originalCTX,String firstSplit,String secondSplit){
        String splitStr=Model.function_processor.getCTX(originalCTX, firstSplit, secondSplit);
        return splitStr;
    }
    public static String checkPacket(String receivedPacket, Socket socket) throws IOException{
        String checkedStr=Model.function_processor.checkPckt(receivedPacket, socket);
        return checkedStr;
    }
    
}
