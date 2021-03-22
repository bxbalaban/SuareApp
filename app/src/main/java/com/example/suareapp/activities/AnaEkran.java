package com.example.suareapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.suareapp.R;

public class AnaEkran extends AppCompatActivity {
private Button button8;
    private Button button5;
    private Button button11;
    private Button button10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran2);

        Button button8=(Button)findViewById(R.id.button8);
        Button button5=(Button)findViewById(R.id.button5);
        Button button11=(Button)findViewById(R.id.button11);
        Button button10=(Button)findViewById(R.id.button10);

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage1=new Intent(AnaEkran.this, KonumGiris.class);
                startActivity(topage1);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage2=new Intent(AnaEkran.this, SuareGiris.class);
                startActivity(topage2);
            }
        });
    }
}