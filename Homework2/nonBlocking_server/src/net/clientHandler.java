/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.nio.channels.SelectionKey;
import controller.functionController;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;

/**
 *
 * @author Lida
 */
public class clientHandler {

    static void acceptHandler(SocketChannel clientChannel) throws IOException, Exception {
        String clientID=clientChannel.socket().getRemoteSocketAddress().toString()
                                +String.valueOf(clientChannel.socket().getPort());
        
        functionController clientFunction= new functionController();
        clientFunction.clientInitial(clientID);
    }
     static void rwHandler(SelectionKey key) throws IOException, SQLException, Exception{
        SocketChannel sc= (SocketChannel) key.channel();
        String clientID=sc.socket().getRemoteSocketAddress().toString()
                                +String.valueOf(sc.socket().getPort());
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
        buffer.clear();  
        int len = 0;  
        StringBuffer stringByte = new StringBuffer();  
        while ((len = sc.read(buffer)) > 0) {  
            buffer.flip();  
            stringByte.append(new String(buffer.array(), 0, len));  
        }  
        
        if(stringByte.length()>0)
        {
        String clientAns = stringByte.toString();
        
        
        //System.out.println(clientAns);
        functionController clientFunction = new functionController();
        clientFunction.clientProc(clientID,clientAns,key);
        
        
        String feedback;
        feedback = clientFunction.clientOut(clientID);
        //System.out.println(feedback);
        ByteBuffer buf = ByteBuffer.wrap(feedback.getBytes());
        //buf.flip();
        sc.write(buf);
        }
    }
     static void writeHandler(SelectionKey key) throws IOException, SQLException, Exception{
        SocketChannel sc= (SocketChannel) key.channel();
        String clientID=sc.socket().getRemoteSocketAddress().toString()
                                +String.valueOf(sc.socket().getPort());
        functionController clientFunction = new functionController();
        String feedback;
        feedback = clientFunction.clientOut(clientID);
        //System.out.println(feedback);
        ByteBuffer buf = ByteBuffer.wrap(feedback.getBytes());
        //buf.flip();
        sc.write(buf);
        //System.out.println(buf);
        //System.out.println("^_^");
        
    }
     public static void quitHandler(SelectionKey key) throws IOException{
         SocketChannel sc= (SocketChannel) key.channel();
         key.cancel();   //Shut down the connection
         sc.close();  
         sc.socket().close(); 
     }
}
