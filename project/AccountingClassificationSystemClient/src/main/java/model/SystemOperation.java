/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import common.RMIInterface;
import static startup.SystemClient.URL;
import java.rmi.Naming;
import net.Receiver;
import net.Sender;
import static startup.SystemClient.getCTX;
/**
 *
 * @author Lida
 */
public class SystemOperation {
    
    static String[] dataUpload;
    
    public static void infoDataIn() throws Exception{
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL);  
        userOperation.firstDataExchange();
        Receiver receiver = new Receiver("Queue");
        Thread receiverThread = new Thread(receiver);
        receiverThread.start();
     
    }
    
    public static void categoryDataUpload(String dataIn) throws Exception{
        
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL);
        
        Sender sender = new Sender("Queue2");
        DataInfo dataInfo = new DataInfo();
        
        dataUpload =  dataIn.split("\n"); 
       
        
        for(int i = 0;i<dataUpload.length;i++){
            
            String dataTemp = "(" + dataUpload[i] + ")";
            String transferInfo = getCTX(dataTemp,"(",">");
            String categoryID = getCTX(dataTemp,"-",")");
            System.out.println("Transfer name & ID: "+transferInfo+" category ID: "+ categoryID);
            dataInfo.setItemName(transferInfo);
            dataInfo.setItemID(categoryID);
            dataInfo.printItem();
            sender.sendMessage(dataInfo);
        }
        
        userOperation.resultUpload();
        
    }
    
    
    public static void checkDataIn(String categoryID) throws Exception{
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL);
        userOperation.checkExchange(categoryID);
    }
    
}
