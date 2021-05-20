package com.example.suareapp.firebase;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.suareapp.activities.IncomingCallsActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //Log.d("FCM","Token"+token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        /*if(remoteMessage.getNotification() != null){
            Log.d(
                    "FCM",
                    "Remote message receiver"+remoteMessage.getNotification().getBody()
             );

         */
        String type=remoteMessage.getData().get(Constants.REMOTE_MSG_TYPE);
        if(type!=null){
                if(type.equals(Constants.REMOTE_MSG_INVITATION)){
                    Intent intent=new Intent(getApplicationContext(), IncomingCallsActivity.class);
                    intent.putExtra(
                            Constants.REMOTE_MSG_MEETING_TYPE,
                            remoteMessage.getData().get(Constants.REMOTE_MSG_MEETING_TYPE)
                    );
                    intent.putExtra(
                            Constants.KEY_NAME,
                            remoteMessage.getData().get(Constants.KEY_NAME)
                    );
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }
    }

