/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lida
 */
public class ServerConnector implements Runnable{
    private Socket socket;
    private static final int portNum = 9999;
    private InetSocketAddress serverAddress;
    private SocketChannel socketChannel;
    private Selector selector;
    private LinkedList<String> sendingQueue = new LinkedList<String>();
    private ByteBuffer bufferFromServer = ByteBuffer.allocateDirect(2048);
    private volatile boolean timeToSend = false;
    
    
    public void connectToServer(String IP) {
        this.serverAddress = new InetSocketAddress(IP, portNum);
        new Thread(this).start();
    }
    @Override
    public void run() {
        try {
            
            serverchannelInit();
            selectorInit();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true){
            try {
                selector.select();
                if(timeToSend){
                    socketChannel.keyFor(selector).interestOps(SelectionKey.OP_WRITE);
                    timeToSend = false;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
             SelectionKey key=null;
             
             while(iterator.hasNext()){
                 key=iterator.next();
                 //System.out.println(key.isWritable());
                 iterator.remove();//erase the key that had been handled to aviod repeatly hanlder 
                 if (!key.isValid()) {
                        continue;
                    }
                 if(key.isConnectable()){
                     continueConncet(key);                     
                 }else if(key.isReadable()){
                     readServer(key);
                 }else if(key.isWritable()){
                     writeServer(key);
                 }
                 
             }
                
            } catch (IOException ex) {
                Logger.getLogger(ServerConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    
    
    
    
    }
    private void selectorInit() throws IOException{
         selector=Selector.open();
         socketChannel.register(selector, SelectionKey.OP_CONNECT);
     }
     private void serverchannelInit() throws IOException{
         socketChannel=SocketChannel.open();
         socketChannel.configureBlocking(false);
         socketChannel.connect(serverAddress);
     }

    private void continueConncet(SelectionKey key) throws IOException {
        socketChannel.finishConnect();
        key.interestOps(SelectionKey.OP_READ);
    }

    private void readServer(SelectionKey key) throws IOException {
        SocketChannel sc= (SocketChannel) key.channel();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.clear();
        int bytesRead = sc.read(buf);
        //System.out.println(bytesRead);
        String ServerAns=new String(buf.array()).trim();
        //System.out.println(ServerAns);
        controller.Controller.Interface(ServerAns);
    
    }

    private void writeServer(SelectionKey key) throws IOException {
        synchronized (sendingQueue){
            while (!sendingQueue.isEmpty()){
                ByteBuffer bufferTosend = ByteBuffer.wrap(sendingQueue.remove().getBytes());
                socketChannel.write(bufferTosend);

                if (bufferTosend.hasRemaining()) return;
            }

        }
        key.interestOps(SelectionKey.OP_READ);
    
    }
    public void leaveServer() throws IOException{
       this.socketChannel.close();
       this.socketChannel.keyFor(selector).cancel();
    }
    
    public void addUserMsgToQueue ( String msg){
        synchronized (sendingQueue) {
            sendingQueue.add(msg);
        }
        this.timeToSend = true;
        selector.wakeup();
    }
    
}
