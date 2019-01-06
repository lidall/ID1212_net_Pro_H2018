/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Lida
 */
import java.rmi.Naming;
import static startup.SystemClient.URL;
import common.RMIInterface;

public class UserChoice {

    public static boolean userLogin(String sendCTX) throws Exception{
        boolean breakFLG = false;
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
        String result=userOperation.loginOperation(sendCTX);
 
        if (result.contains("^")){
            breakFLG = true;
            result = result.replace("^", "");
        }
        System.out.println(result);
        return breakFLG;
    }
    
    public static boolean userRegister(String sendCTX) throws Exception{
        boolean breakFLG = false;
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
        String result=userOperation.registerOperation(sendCTX);
   
        if (result.contains("^")){
            breakFLG = true;
            result = result.replace("^", "");
        }
        System.out.println(result);
        return breakFLG;
    }
}