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

String loginOperation(String userInput)throws RemoteException; 
String registerOperation(String userInput)throws RemoteException;  
void firstDataExchange() throws RemoteException;
void resultUpload() throws RemoteException;
void checkExchange(String categoryID) throws RemoteException;



}
