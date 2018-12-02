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

public interface RMIInterface extends Remote{ 

String loginSystem(String name)throws RemoteException; 
String operationSystem(String operation)throws RemoteException;    
void uploadProcess(String userFileName) throws RemoteException;
void downloadProcess(String userFileName) throws RemoteException;
void updateProcess(String userFileName) throws RemoteException;
String deleteProcess(String userFileName)throws RemoteException;
boolean call(ICallback callback) throws RemoteException;
String callReminder(ICallback callback, String userName, String fileName, boolean opChoice) throws RemoteException;

} 