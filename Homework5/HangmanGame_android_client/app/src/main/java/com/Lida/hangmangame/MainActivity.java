package com.Lida.hangmangame;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.view.*;
import android.content.Intent;
import android.os.*;


import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private Button GameStartButton;
    private EditText serverIPText;
    public static String SERVER_ADDR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        serverIPText = (EditText) findViewById(R.id.serverIPText);

        GameStartButton = ((Button) findViewById(R.id.GameStartButton));
        GameStartButton.setEnabled(true);
        GameStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                FirebaseMessaging.getInstance().subscribeToTopic("contest");

                try {


                    SERVER_ADDR = serverIPText.getText().toString();
                    System.out.println("WOW:" + SERVER_ADDR);
                    Intent intent =new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(intent);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        }

    }

