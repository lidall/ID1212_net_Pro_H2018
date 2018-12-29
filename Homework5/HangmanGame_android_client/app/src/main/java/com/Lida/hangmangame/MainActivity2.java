package com.Lida.hangmangame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.*;
import android.widget.*;
import android.view.*;
import static com.Lida.hangmangame.fcmHandle.*;

public class MainActivity2 extends AppCompatActivity {
    private EditText etMain;
    private Button btnMain;
    private TextView tvMain;
    private ClientThread mClientThread;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etMain = (EditText) findViewById(R.id.editText);
        btnMain = (Button) findViewById(R.id.guessButton);
        tvMain = (TextView) findViewById(R.id.resultView);

        mHandler=new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    String rcvCTX = msg.obj.toString();
                    String rcvIP = Multiclient.getCTX(rcvCTX,"/",")");
                    String localIP =ClientThread.CLIENT_ADDR+")";
                    localIP = Multiclient.getCTX(localIP,"/",")");
                    String rContent = Multiclient.getCTX(rcvCTX,"[","]");
                    if(rcvIP.equals(localIP)) {

                        tvMain.append(rContent);

                        if (MESSAGE_COME){
                            new AlertDialog.Builder(MainActivity2.this)
                                    .setTitle("Contest")
                                    .setMessage(NOTIFICATION_BODY)
                                    .setPositiveButton("OK!", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            MESSAGE_COME = false;

                                        }
                                    })
                                    .show();}

                    }
                }
            }
        };

        //点击button时，获取EditText中string并且调用子线程的Handler发送到服务器
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = ClientThread.CLIENT_ADDR+")"+"["+etMain.getText().toString()+"]";
                    mClientThread.revHandler.sendMessage(msg);
                    etMain.setText("");
                    tvMain.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        mClientThread = new ClientThread(mHandler);
        new Thread(mClientThread).start();


    }
}
