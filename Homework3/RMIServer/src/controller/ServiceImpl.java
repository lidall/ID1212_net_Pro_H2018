
package controller;
/**
 *
 * @author Lida
 */
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import common.RMIInterface; 
import model.FileChoice;
import model.LoginChoice;
import java.util.logging.Level;
import java.util.logging.Logger;
import common.ICallback;
import static Serverstartup.RMIServer.workingFLG;
import model.ReminderThread;

public class ServiceImpl extends UnicastRemoteObject implements RMIInterface{ 

    
public ServiceImpl() throws RemoteException { 
} 

@Override 
public String loginSystem(String userInput) throws RemoteException { 
// TODO Auto-generated method stub 
    
    String userChoice = getCTX(userInput,"(","@");
    String returnWord = null;
    switch (userChoice)
    {
        case "1" :{
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
                break;
            }
            returnWord = LoginChoice.userLogin(userInput);
            break;
            } catch (SQLException e) {
             e.printStackTrace();
        }
        }
        case "2" :{
            returnWord = LoginChoice.userRegister(userInput);
            break;  
        }

        
    }
    return String.format(returnWord);
} 


@Override 
public String operationSystem(String userOperation) throws RemoteException { 
    String returnWord = null;
    String userChoice = getCTX(userOperation,"(","@");
    String userName = getCTX(userOperation,"@","#");
    switch (userChoice)
    {
        case "0" :{
            returnWord = null;
            returnWord =FileChoice.getFileList(userName);
            break;
        }
        case "1" :{
            returnWord = null;
            returnWord =FileChoice.uploadInitial(userName);
            break;
        }
        case "2" :{
            returnWord = null;
            returnWord =FileChoice.downloadInitial(userName);
            break;
        }
        case "3" :{
            returnWord = null;
            returnWord =FileChoice.updateInitial(userName);
            break; 
        }
        case "4" :{
            returnWord = null;
            returnWord =FileChoice.deleteInitial(userName);
            break;  
        }
       case "5" :{
            returnWord = LoginChoice.userUnregister(userOperation);
            break;
        } 
    }
    return String.format(returnWord);
}

@Override
public void uploadProcess(String userFileName) throws RemoteException{
    
    try {
        FileChoice.tcpUpload(userFileName);
    } catch (Exception ex) {
        Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
}


@Override
public void downloadProcess(String userFileName) throws RemoteException{
    
    try {
        FileChoice.tcpDownload(userFileName);
    } catch (Exception ex) {
        Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
}


@Override
public void updateProcess(String userFileName) throws RemoteException{
    
    try {
        FileChoice.tcpUpdate(userFileName);
    } catch (Exception ex) {
        Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
}


@Override 
public String deleteProcess(String userfileName) throws RemoteException {
       String result = FileChoice.fileDelete(userfileName);
        return result;
} 

 @Override
    public boolean call(ICallback callback) throws RemoteException {
        System.out.println("From Client : Query");
        while(workingFLG){
            continue;
        }
        callback.callback(true);
        workingFLG = true;

        return true;
    }
   
 @Override
    public String callReminder(ICallback callback, String userName, String fileName, boolean opChoice) throws RemoteException {
        
        
        new ReminderThread(userName, fileName, callback, opChoice).start();
        
        return "done";
    }

public static String getCTX(String originalCTX,String firstSplit,String secondSplit){
        String resultCTX = originalCTX.substring(originalCTX.lastIndexOf(firstSplit), 
        originalCTX.lastIndexOf(secondSplit));
        resultCTX = resultCTX.substring(1,resultCTX.length());
        return resultCTX;
    }

} 