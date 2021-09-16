package com.example.suareapp.MedicineReminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.suareapp.R;

public class AlarmField extends AppCompatActivity {
MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_field);


        String  data=getIntent().getStringExtra("Key");

        ImageButton stopbutton=(ImageButton)findViewById(R.id.stopalarm);

        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
                finish();
            }
        });

        TextView tw=(TextView) findViewById(R.id.comments1);
        tw.setText(data);
        playMusic();
    }
    public void playMusic(){
        if(mediaPlayer==null){
            mediaPlayer= MediaPlayer.create(this, R.raw.suaremedia);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        mediaPlayer.start();
    }

    public void stopMusic(){ stopPlayer();}

    public void stopPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopMusic();
    }
}