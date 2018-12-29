package com.Lida.hangmangame;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Lida on 2018/12/5.
 */

public class ClientThread implements Runnable {
    private Socket mSocket;
    private BufferedReader mBufferedReader = null;
    private OutputStream mOutputStream = null;
    private Handler mHandler;
    public static String CLIENT_ADDR;
    public Handler revHandler;

    public ClientThread(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void run() {
        try {
            mSocket = new Socket(MainActivity.SERVER_ADDR,9999);

            Log.d("xjj","connect success");
            mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            mOutputStream = mSocket.getOutputStream();
            CLIENT_ADDR = mSocket.getLocalAddress().toString();

            mOutputStream.write((CLIENT_ADDR+ "\n").getBytes("utf-8") );

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        String content = null;
                        while ((content = mBufferedReader.readLine()) != null) {
                            Log.d("xjj",content);
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = content;
                            mHandler.sendMessage(msg);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }.start();

            Looper.prepare();
            revHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        try {

                            if(msg.obj.toString().equals("QUIT"))
                            {
                                mSocket.close();
                                System.exit(0);
                            }

                            mOutputStream.write((msg.obj.toString() + "\r\n").getBytes("utf-8"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
                }
            };
            Looper.loop();





        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
