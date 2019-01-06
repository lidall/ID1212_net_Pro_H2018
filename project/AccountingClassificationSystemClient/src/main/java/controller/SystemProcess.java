/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.SystemOperation;
import net.Receiver;
import view.SelectionOperation;
import java.awt.Robot;

/**
 *
 * @author Lida
 */
public class SystemProcess {
    
    public static void infoDataProcess() throws Exception{
        
        SystemOperation.infoDataIn();                
        SelectionOperation.selectionMain();          
    }
    
    public static void dataUploadProcess(String dataIn) throws Exception{
        SystemOperation.categoryDataUpload(dataIn);    
    }
    
    public static void checkProcess(String categoryID) throws Exception{
            Receiver receiver = new Receiver("Queue3");      
            Thread receiverThread = new Thread(receiver);
            receiverThread.start();                      
            SystemOperation.checkDataIn(categoryID);         
            System.out.println("Waiting for msg");        
            new Robot().delay(100);                      
    }
    
}
