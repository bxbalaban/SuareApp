package com.example.suareapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.suareapp.R;

public class FitnessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);

        Button level1=(Button)findViewById(R.id.level1);
        Button level2=(Button)findViewById(R.id.level2);
        Button level3=(Button)findViewById(R.id.level3);
        ImageView fitness_back=(ImageView)findViewById(R.id.fitness_back);

        fitness_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage1=new Intent(FitnessActivity.this, AnaEkran.class);
                startActivity(topage1);
            }
        });

        Intent intent = new Intent(getApplicationContext(), YoutubeActivity.class);

        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("level","level1");
                startActivity(intent);
            }
        });

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("level","level2");
                startActivity(intent);
            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("level","level3");
                startActivity(intent);
            }
        });


    }
}