/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
/**
 *
 * @author Lida
 */
import model.UserChoice;
import view.cmdLine;
/*
    This class is for user to login or register. After the process is done, the 
    function will return a boolean breakFLG. If breakFLG is true, which means the
    client login or register to the server successfully, the user can access to the
    server and choose the file service. Otherwise, the client won't access to the system,
    and repeat to choose login or register service.
    */
public class FirstChoiceJudge {
    
    public static boolean firstJudge() throws Exception{
        
        boolean breakFLG = false;
        
        String userAttempt = cmdLine.firstJudgeCMD();
        
        switch (userAttempt)
        {
            case "1" :{
                breakFLG = UserChoice.userLogin();
                break;
            }
            case "2" :{
                breakFLG = UserChoice.userRegister();
                break;
                
            }
            case "3" :{
                System.exit(0);   
            }
            default: {
                cmdLine.println("Illegal Input");
                    break;
            }
            
        }
         return breakFLG;
    }
    
}
