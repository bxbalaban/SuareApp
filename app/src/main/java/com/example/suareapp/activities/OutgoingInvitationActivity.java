package com.example.suareapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suareapp.R;
import com.example.suareapp.models.User;

import org.w3c.dom.Text;

public class OutgoingInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_invitation);

        ImageView imageMeetingType=findViewById(R.id.imageMeetingType);
        String meetingType=getIntent().getStringExtra("type");
        if(meetingType!=null){
            if(meetingType.equals("video")){
                imageMeetingType.setImageResource(R.drawable.ic_video);
            }
        }

        TextView textFirstChar=findViewById(R.id.textFirstChar);
        TextView textUserNAme=findViewById(R.id.textUserName);

        User user=(User) getIntent().getSerializableExtra("user");
        if(user!=null){
            textFirstChar.setText(user.name.substring(0,1));;
            textUserNAme.setText(user.name);
        }

        ImageView imageStopCall=findViewById(R.id.imageStopInvitation);
        imageStopCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}