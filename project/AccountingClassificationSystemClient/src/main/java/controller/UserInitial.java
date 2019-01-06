
package controller;

import static startup.SystemClient.URL;
import static startup.SystemClient.SERVER_IP;
import view.SetServerAddr;
public class UserInitial {

    public static void initialInter(){
         SetServerAddr.initialMain();  
    }
    
    
    public static void userInitial(){  
        URL ="rmi://"+SERVER_IP+":9999/rmi.server.controller.ServiceImpl";
    }
    
}
