package multithread.net;
import multithread.model.DataProcessing;
import multithread.model.Database;
import java.io.*;
import java.net.*;
import java.util.*;
import multithread.controller.UserProcess;

/*
This class is used for the main operation after the initial round.
*/

public class ServerThread extends Thread implements Runnable {

    Socket socket;
    ArrayList<Socket> LIST;
    static boolean STOP_FLG = false;
    private Socket mSocket = null;
    private BufferedReader mBufferedReader = null;
    
    
    public ServerThread(Socket socket, ArrayList<Socket> list) {
        this.socket = socket;
        this.LIST = list;
    }
    
 
    @Override
    public void run() {
        try {
            while(!STOP_FLG){
                mSocket = socket;
                mBufferedReader = new BufferedReader(
                new InputStreamReader(mSocket.getInputStream(), "utf-8"));
                OutputStream os = socket.getOutputStream();

                String rcvCTX = mBufferedReader.readLine();
                System.out.println(rcvCTX);
                String sendCTX = null;
                //Extracting the message about whether to continue the game or 
                //not with the flag continueFLG.
                System.out.println("server recevied:"+ rcvCTX);
                sendCTX = UserProcess.userProcess(rcvCTX);

                System.out.println("server sent:"+ sendCTX);
                os.write((sendCTX+"\n").getBytes("utf-8"));
                // The result will be sent to the client.
            }
            
        } catch (IOException e) {
            e.printStackTrace();    
        }
    }

}
