/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author Lida
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICallback extends Remote {


    boolean callback(boolean processflag) throws RemoteException;
    String callbackReminder(String userName, String fileName, String Operation) throws RemoteException;
    

}

