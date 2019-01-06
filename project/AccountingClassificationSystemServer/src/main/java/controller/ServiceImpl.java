
package controller;

import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.RMIInterface;
import model.LoginChoice;
import model.SystemOperation;
import net.Receiver;

public class ServiceImpl extends UnicastRemoteObject implements RMIInterface{ 

    
public ServiceImpl() throws RemoteException { 
} 

    @Override 
    public String loginOperation(String userInput) throws RemoteException { 
    // TODO Auto-generated method stub 

        String returnWord = null;

        try{
            Connection conn = SystemInitial.getConn();
            String sql = "select * from UserInfo";
            PreparedStatement pstmt;

            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int rowCount = 0;  
        while(rs.next()) {  
            rowCount++;  
        }
        if(rowCount<1){
            returnWord = "No user in the Database. Please create an account";
        }else{
             returnWord = LoginChoice.userLogin(userInput);
        }    
        } catch (SQLException e) {
         e.printStackTrace();
    }
        return String.format(returnWord);
    } 


    @Override 
    public String registerOperation(String userInput) throws RemoteException { 
        String returnWord = null;
        returnWord = LoginChoice.userRegister(userInput);
        return String.format(returnWord);
    }

    @Override
    public void firstDataExchange(){

        SystemOperation.firstDataProcess();   

    }

    @Override
    public void resultUpload(){ 

        try{
        Receiver receiver = new Receiver("Queue2");
            Thread receiverThread = new Thread(receiver);  
            receiverThread.start();}
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void checkExchange(String categoryID){

        SystemOperation.checkDataProcess(categoryID);   
   

    }

} 