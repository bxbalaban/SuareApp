package com.example.suareapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suareapp.R;
import com.example.suareapp.firebase.Constants;

public class IncomingCallsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_calls);

        ImageView imageMeetingType=findViewById(R.id.imageMeetingType);
        String meetingType=getIntent().getStringExtra(Constants.REMOTE_MSG_MEETING_TYPE);

        if(meetingType!=null){
            if(meetingType.equals("video")){
                imageMeetingType.setImageResource(R.drawable.ic_video);
            }
        }
        TextView textFirstChar=findViewById(R.id.textFirstChar);
        TextView textUserName=findViewById(R.id.textUserName);

        String name=getIntent().getStringExtra(Constants.KEY_NAME);

        if(name!=null){
            textFirstChar.setText(name.substring(0,1));
        }
        textUserName.setText(name);


    }
}