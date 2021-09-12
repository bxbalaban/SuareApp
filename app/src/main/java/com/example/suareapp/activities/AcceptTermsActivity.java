package com.example.suareapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suareapp.R;

import java.util.Locale;

public class AcceptTermsActivity extends AppCompatActivity {
    String text;

    TextToSpeech TTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_terms);
        ScrollView scroll=(ScrollView)findViewById(R.id.termsScrollText);
        scroll.setScrollbarFadingEnabled(false);
        Button termsCancel=(Button)findViewById(R.id.termsCancel);
        Button termsAccept=(Button)findViewById(R.id.termsAccept);
        Button termsListen=(Button)findViewById(R.id.termsListen);
        TextView termsText=(TextView)findViewById(R.id.termsText);

        text= (String) termsText.getText();

        termsAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage1=new Intent(AcceptTermsActivity.this, FitnessActivity.class);
                startActivity(topage1);
            }
        });
        termsCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage1=new Intent(AcceptTermsActivity.this, AnaEkran.class);
                startActivity(topage1);
            }
        });
        termsListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    StartSpeak(text);

            }
        });
    }
    private void StartSpeak(final String data) {

        TTS=new TextToSpeech(AcceptTermsActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int initStatus) {
                if (initStatus == TextToSpeech.SUCCESS) {
                    if(TTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                        TTS.setLanguage(Locale.US);
                    TTS.setPitch(1.3f);
                    TTS.setSpeechRate(0.7f);
                    // start speak
                    speakWords(data);
                }
                else if (initStatus == TextToSpeech.ERROR) {
                    Toast.makeText(getApplicationContext(), "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
                }
            }


        });
    }
    private void speakWords(String speech) {
        TTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }
}