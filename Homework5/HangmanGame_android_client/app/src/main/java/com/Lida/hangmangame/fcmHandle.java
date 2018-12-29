package com.Lida.hangmangame;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class fcmHandle extends FirebaseMessagingService {
    public fcmHandle() {
    }

    public static boolean MESSAGE_COME = false;
    public static String NOTIFICATION_BODY;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("Service", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("Data", "Message data payload: " + remoteMessage.getData());


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("Notification", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            MESSAGE_COME = true;
            NOTIFICATION_BODY = remoteMessage.getNotification().getBody();
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
