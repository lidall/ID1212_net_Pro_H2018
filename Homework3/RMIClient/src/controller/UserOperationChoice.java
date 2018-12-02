
package controller;
/**
 *
 * @author Lida
 */
import model.UserChoice;
import java.rmi.Naming;
import startup.RMIClient;
import static startup.RMIClient.URL;
import view.cmdLine;
import common.RMIInterface;

public class UserOperationChoice {
    
    public static boolean operationJudge() throws Exception{
        boolean breakFLG = false;
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
        String userAttempt = cmdLine.operationJudgeCMD();
        //The user's choice will be encapsulted into "(the number of service@username#"
        //and sent to the server to extract the message and deal with the request. 
        //Then the server will send the result back.
        switch (userAttempt)
       {
            case "0" :{
                String result = null;
                result = userOperation.operationSystem("(0@"+RMIClient.USER_NAME+"#");
                cmdLine.println("Your file list: \n"+result);
                while(true){
                    String quitFLG = cmdLine.quitFLG();
                    if (quitFLG.equals("quit")||quitFLG.equals("Quit"))
                        break;
                }
                break;
            }
            
            case "1" :{
                String result = null;
                result = userOperation.operationSystem("(1@"+RMIClient.USER_NAME+"#");
                UserChoice.userUploadProcess(result);
                
                break;
            }
            case "2" :{
                String result = null;
                result = userOperation.operationSystem("(2@"+RMIClient.USER_NAME+"#");
                UserChoice.userDownloadProcess(result);
                
                break;
                
            }
             case "3" :{
                String result = null;
                result = userOperation.operationSystem("(3@"+RMIClient.USER_NAME+"#");
                UserChoice.userUpdateProcess(result);
                break;
                
            }
              case "4" :{
                String result = null;
                result = userOperation.operationSystem("(4@"+RMIClient.USER_NAME+"#");
                UserChoice.userDeleteProcess(result);
                break;
                
            }
            case "5" :{
                String result = null;
                result = UserChoice.unregisterProcess();
                cmdLine.println(result);
                break;
           
            }
            case "6" :{
                String result = null;
                result = RMIClient.USER_NAME+" log out!";
                RMIClient.USER_NAME = null;
                RMIClient.USER_NAME_TEMP = null;
                cmdLine.println(result);
                breakFLG = true;
                break;
            }
             default: {
                cmdLine.println("Illegal input");
                        break;
            }
             
            
    }
        return breakFLG; 
    
}
}
