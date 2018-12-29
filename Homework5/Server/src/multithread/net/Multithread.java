package multithread.net;
import multithread.controller.UserInitial;
import java.io.*;
import java.net.*;
import java.util.*;

public class Multithread {
    
    public static String[] WORD_BASE;
    private static final ArrayList<Socket> SOCKET_LIST = new ArrayList<Socket>();
    public static ArrayList<String> USER_BASE = new ArrayList<String>();
    
    
    public static Socket mSocket = null;
    public static BufferedReader mBufferedReader = null;
    
    public static void main(String[] args) throws Exception {
        
        // On the server side, it will run on port 9999.
        ServerSocket server = new ServerSocket(9999);
        System.out.println("SYSTEM START");
        /*
        Extracting the txt file "word.txt", and importing it into an string array 
        "WORD_BASE" to store all the words as a library.
        */
        File file = new File("/Users/harry/Desktop/KTH_Course/Year_1/Period 2/ID1212/Task 1/words.txt");
        FileReader reader = new FileReader(file);
        int fileLen = (int)file.length();
        char[] chars = new char[fileLen];
        reader.read(chars);
        String txt = String.valueOf(chars);
        WORD_BASE = txt.split("\n"); 
       
        
        while(true){
            /*
            Accepting and saving all the socket connection into arraylist 
            "SOCKET_LIST".
            Building up a database "USER_BASE" to store the client IP, word that
            the client is guessing, the word length and word replacing by underline,
            which will be updated after every guessing.
            */
            Socket socket = server.accept();
            SOCKET_LIST.add(socket);
            OutputStream os = socket.getOutputStream();
            mSocket = socket;
            mBufferedReader = new BufferedReader(
              new InputStreamReader(mSocket.getInputStream(), "utf-8"));

            String rcvCTX = mBufferedReader.readLine() +")";

            String rcvIP = Multithread.getCTX(rcvCTX,"/",")");
            System.out.println("connect success! " + rcvIP);
            String sendCTX = null;
            sendCTX = UserInitial.userInitial(rcvIP);
            os.write((sendCTX+"\n").getBytes("utf-8"));
            new ServerThread(socket,SOCKET_LIST).start();
        }
         
    }
    /*
    This static method is used for extracting content from an original packaged message
    */
    public static String getCTX(String originalCTX,String firstSplit,String secondSplit)
    {
        String resultCTX = originalCTX.substring(originalCTX.lastIndexOf(firstSplit), 
                originalCTX.lastIndexOf(secondSplit));
        resultCTX = resultCTX.substring(1,resultCTX.length());
        return resultCTX;
    }
}


