package net;

import java.io.*;  
import java.net.*;  
import java.nio.*;  
import java.nio.channels.*; 
import java.sql.SQLException;
import java.util.*; 
import java.util.logging.*;


public class clientConnection extends Thread implements Runnable{  
    private Selector selector;  
    StringBuffer stringByte = new StringBuffer();  
    SelectionKey ssKey;  

    public void init() {  
        try {  
            selector = Selector.open();  
            ServerSocketChannel ssChannel = ServerSocketChannel.open();  
            ssChannel.configureBlocking(false);  
            ssChannel.socket().bind(new InetSocketAddress(9999));  
            ssKey = ssChannel.register(selector, SelectionKey.OP_ACCEPT);  
            view.viewCLI.printCLI("server is starting..." + new Date());  
        } catch (IOException ex) {  
            Logger.getLogger(clientConnection.class.getName()).log(Level.SEVERE,  
                    null, ex);  
        }  
    } 
     public void execute() {  
        try {  
            while (true) {  
                int num = selector.select();  
                if (num > 0) {  
                    Iterator<SelectionKey> it = selector.selectedKeys()  
                            .iterator();  
                    while (it.hasNext()) {  
                        SelectionKey key = it.next();  
                        it.remove();  
                        if (!key.isValid())  
                            continue;  
                        if (key.isAcceptable()) {   
                            getConn(key);  
                        } else if (key.isReadable()|key.isWritable()) {  
                            readWriteMsg(key);  
                        }  
                        else 
                            break;  

                    }  

                }  
          
            }  

        } catch (Exception ex) {  
            Logger.getLogger(clientConnection.class.getName()).log(Level.SEVERE,  
                    null, ex);  
        }  
    }  

    private void getConn(SelectionKey key) throws IOException, Exception {  
        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();  
        SocketChannel clientChannel = ssChannel.accept();  
        clientChannel.configureBlocking(false);  
        clientChannel.register(selector, SelectionKey.OP_READ);  
        view.viewCLI.printCLI("b"
                + "uild connection :" 
                + clientChannel.socket().getRemoteSocketAddress());
        clientHandler.acceptHandler(clientChannel);//the process after accept.
        String sendCTX = "0";
        clientChannel.write(ByteBuffer.wrap(sendCTX.getBytes()));
               
        }
        
        
      

    private void readWriteMsg(SelectionKey key) throws Exception, SQLException {  
        clientHandler.rwHandler(key);
       
    }  

@Override    
    public void run() {  
        init();  
        execute();  
    }  
}