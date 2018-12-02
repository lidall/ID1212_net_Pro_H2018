package controller;
import common.ICallback;
import view.cmdLine;
/**
 *
 * @author Lida
 */
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import startup.RMIClient;

public class CallbackImpl extends UnicastRemoteObject implements ICallback {

    public CallbackImpl() throws RemoteException {
    }

    @Override
    public boolean callback(boolean flag) throws RemoteException {

        cmdLine.println("From Server: Process Complete");
        
        return flag;
    }
    
    @Override
    public String callbackReminder(String userName, String fileName, String Operation) throws RemoteException{
        if (!userName.equals(RMIClient.USER_NAME)){
        if (Operation.equals("update")){
        cmdLine.println("\nAttention!! "+userName + " has updated your file: " + fileName);
        cmdLine.println("If nothing wrong, just continue your job :)");}
        else if (Operation.equals("download")){
        cmdLine.println("\nAttention!! "+userName + " has downloaded your file: " + fileName);
        cmdLine.println("If nothing wrong, just continue your job :)");}}
        return "done";
    }
    
}
