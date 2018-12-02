
package model;

import common.ICallback;
import java.rmi.Naming;
import static startup.RMIClient.URL;
import common.RMIInterface;
import controller.CallbackImpl;
import java.io.File;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import net.DownloadTCP;
import net.UpdateTCP;
import net.UploadTCP;
import startup.RMIClient;
import static startup.RMIClient.URL;
import static startup.RMIClient.getCTX;
import view.cmdLine;
/**
 *
 * @author Lida
 */
public class UserChoice {

    public static boolean userLogin() throws Exception{
        boolean breakFLG = false;
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
        String sendCTX = cmdLine.userLoginCMD();
        String result=userOperation.loginSystem(sendCTX);
        //The result will be get from the server, if the result contains "^", 
        //which means the user login to the system successfully.
        //Set breakFLG true to jump out of the cycle in function firstjudge
        if (result.contains("^")){
            breakFLG = true;
            result = result.replace("^", "");
            RMIClient.USER_NAME = RMIClient.USER_NAME_TEMP;
            result = "Sucess! Welcome user: " + RMIClient.USER_NAME;
        }
        else{
            result = "Wrong user Name or Password";
        }
        cmdLine.println(result);
        return breakFLG;
    }
    
    public static boolean userRegister() throws Exception{
        boolean breakFLG = false;
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
        String sendCTX = cmdLine.userRegisterCMD();
        String result=userOperation.loginSystem(sendCTX);
        //The result will be get from the server, if the result contains "^", 
        //which means the user register to the system successfully.
        //Set breakFLG true to jump out of the cycle in function firstjudge
        if (result.contains("^")){
            breakFLG = true;
            RMIClient.USER_NAME = RMIClient.USER_NAME_TEMP;
            result = result.replace("^", "");
            result = "Create user Sucessfully! Welcome user: " + RMIClient.USER_NAME;
        }else{
            result = "Wrong! User exists!";
        }
        cmdLine.println(result);
        return breakFLG;
    }
    
    public static void userDeleteProcess(String result) throws Exception{
    /*
    After the server return the file name separated into different types, the
    client will first bind to a remote interface. Since the user only have authentication
    to delete his private file and public file with all authentication, only these
    two type will be displayed to the user.
    */
    String personalFile =RMIClient.getCTX(result, "<", ">");
    String publicAll = RMIClient.getCTX(result, "{", "}");
    cmdLine.println(personalFile+"\n"+publicAll);
    RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
    cmdLine.println("Please input your filename: (if you want to quit delete process just type \"Quit\")");

    while(true){
        String fileName = cmdLine.getFileName();
        if(fileName.equals("Quit")||fileName.equals("quit")){
            break;
        }
        else if (personalFile.contains(fileName)){
            result = userOperation.deleteProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<private:all>");
            String successFlag=getCTX(result,"&","*");
            String FileFlag=getCTX(result,"*","!");
            String prtresult=RMIClient.USER_NAME+successFlag+FileFlag;
            cmdLine.println(prtresult);
            break;
        }
        else if (publicAll.contains(fileName)){
            result = userOperation.deleteProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:all>");
            String successFlag=getCTX(result,"&","*");
            String FileFlag=getCTX(result,"*","!");
            String prtresult=RMIClient.USER_NAME+successFlag+FileFlag;
            cmdLine.println(prtresult);
            break;
        }


                else{
                    cmdLine.println("File dosen't exists! Please type a file name!");
                }
            }
    }
    
     public static void userDownloadProcess(String result) throws Exception{
        String personalFile =RMIClient.getCTX(result, "<", ">");
        String publicAll = RMIClient.getCTX(result, "{", "}");
        String publicRead =RMIClient.getCTX(result, "!", "?");
        String fileName = null;
        cmdLine.println(personalFile+"\n"+publicAll+"\n"+publicRead);
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
        cmdLine.println("Please input the file you want to download: "
                + "(if you want to quit download process just type \"Quit\")");
    
        while(true){
            fileName = cmdLine.getFileName();

            if(fileName.equals("Quit")||fileName.equals("quit")){
                break;
            }
            else if (personalFile.contains(fileName)){
                userOperation.downloadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<private:all>");
                DownloadTCP.userTCPDownload(fileName);
                ICallback callback = new CallbackImpl();
                userOperation.call(callback);
                UnicastRemoteObject.unexportObject(callback, false);
                cmdLine.println("File download success");
                break;
            }
            else if (publicAll.contains(fileName)){
                userOperation.downloadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:all>");
                DownloadTCP.userTCPDownload(fileName);
                ICallback callback = new CallbackImpl();
                userOperation.call(callback);
                UnicastRemoteObject.unexportObject(callback, false);
                cmdLine.println("File download success");
                break;
            }
            else if (publicRead.contains(fileName)){
                userOperation.downloadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:read>");
                DownloadTCP.userTCPDownload(fileName);
                ICallback callback = new CallbackImpl();
                userOperation.call(callback);
                UnicastRemoteObject.unexportObject(callback, false);
                cmdLine.println("File download success");
                break;
            }

            else{
                
                System.out.println("File dosen't exists!");
                
                }
            }
        } 
     
        public static String unregisterProcess() throws Exception{
        String result = null;
        Scanner sc = new Scanner(System.in);
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL);
        String userConfirm = cmdLine.userUnregisterCMD();
        while (true){
            if (userConfirm.equals("Y")||userConfirm.equals("N"))
                    {
                        break;
                    }
            System.out.println("Please enter \"Y\" or \"N\" !");
            userConfirm = cmdLine.unregisterConf();
        }
        switch (userConfirm){
            case "Y":{
                result = userOperation.operationSystem("(5@"+RMIClient.USER_NAME+"#");
                result = "Sucess! User Removed: " + RMIClient.USER_NAME;
                cmdLine.println(result);
                System.exit(0);
                break;
            }
            case "N" :{
                result = "Please choose new operation.";
                break;
            }
        }
        return result;
    }
        
        public static void userUpdateProcess(String result) throws Exception{
        String personalFile =RMIClient.getCTX(result, "<", ">");
        String publicAll = RMIClient.getCTX(result, "{", "}");
        String fileName = null;
        String userPath=null;
        cmdLine.println(personalFile+"\n"+publicAll);
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
        cmdLine.println("Please input the file path you want to update: (if you want to quit update process just type \"Quit\")");
        while(true){
            userPath = cmdLine.getFilePath();
            if (userPath.contains("/")){
            fileName = userPath.substring(userPath.lastIndexOf("/"), 
                userPath.length());
            fileName = fileName.substring(1,fileName.length());
            }

        if(userPath.equals("Quit")||userPath.equals("quit")){
            break;
        }
        else if (personalFile.contains(fileName)&&new File(userPath).exists()){
            userOperation.updateProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<private:all>");
            UpdateTCP.userTCPUpdate(userPath);
            ICallback callback = new CallbackImpl();
            userOperation.call(callback);
            UnicastRemoteObject.unexportObject(callback, false);
            cmdLine.println("File update success");
            break;
        }
        else if (publicAll.contains(fileName)&&new File(userPath).exists()){
            userOperation.updateProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:all>");
            ICallback callbackReminder = new CallbackImpl();
            new ReminderThread(callbackReminder, RMIClient.USER_NAME, fileName, true).start();
            UpdateTCP.userTCPUpdate(userPath);
            
            cmdLine.println("File update success");
            break;
        }


        else{
            cmdLine.println("File dosen't exists! Please type a correct path!");
        }
        }   



    }
           
public static void userUploadProcess(String result) throws Exception{
    System.out.println(result);
    RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
    cmdLine.println("Please input your file path: (if you want to quit upload process just type \"Quit\")");
    while(true){
        String userPath = cmdLine.getFilePath();
        String fileName = null;
        if (userPath.contains("/")){
        fileName = userPath.substring(userPath.lastIndexOf("/"), 
            userPath.length());
        fileName = fileName.substring(1,fileName.length());
        }
        if(userPath.equals("Quit")||userPath.equals("quit")){
            break;
        }
        else if (fileName == null){
            cmdLine.println("File doen't exist. Please enter a correct path!");
        }
        else if (result.contains(fileName)){
            cmdLine.println("File already exists. Please change your file name!");
        }
        else if(!new File(userPath).exists()){
            cmdLine.println("File doen't exist. Please enter a correct path!");
        }
        else{
            cmdLine.println("\nYour file is \"Public(Read only)\" or \"Public(All permission)\" or \"Private\"! Please type: ");

            while(true){
                String publicFLG =cmdLine.fileFeatureSele();
                if (publicFLG.equals("1")){
                    userOperation.uploadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:read>");
                    ICallback callbackReminder = new CallbackImpl();
                    new ReminderThread(callbackReminder, RMIClient.USER_NAME, fileName, false).start();
                    UploadTCP.userTCPUpload(userPath);
                    break;
                }
                else if (publicFLG.equals("2"))
                {
                    userOperation.uploadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:all>");
                    ICallback callbackReminder = new CallbackImpl();
                    new ReminderThread(callbackReminder, RMIClient.USER_NAME, fileName, true).start();
                    UploadTCP.userTCPUpload(userPath);
                    break;
                }
                else if (publicFLG.equals("3"))
                {
                    userOperation.uploadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<private:all>");
                    UploadTCP.userTCPUpload(userPath);
                    ICallback callback = new CallbackImpl();
                    userOperation.call(callback);
                    UnicastRemoteObject.unexportObject(callback, false);
                    break;
                }
                else{
                    cmdLine.println("Illegal input!");
                }
            }

            
            cmdLine.println("File upload success");
            break;
        }
    }
}
        
    
}
