/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import Serverstartup.RMIServer;
/**
 *
 * @author Lida
 */
public class SystemInitial {
    public static void interfaceInitial() throws Exception{
    int port=9999; 
    Scanner sc = new Scanner(System.in);
    System.out.println("Please enter this server address:");
    RMIServer.SERVER_IP = sc.nextLine();
    String url= "rmi://"+RMIServer.SERVER_IP+":9999/rmi.server.controller.ServiceImpl"; 
    LocateRegistry.createRegistry(port); 
    Naming.rebind(url, new ServiceImpl()); 
    System.out.println("Service Begin!");
    }
    
public static Connection getConn() {
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/UserFileSystem?useSSL=false";
    String username = "root";
    String password = "";
    Connection conn = null;
    try {
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(url, username, password);
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
    return conn;
}
}
