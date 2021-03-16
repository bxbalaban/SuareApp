package com.example.suareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class KayitEkrani extends AppCompatActivity {
private Button get_otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekrani);

        Button get_otp=(Button)findViewById(R.id.get_otp);

        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage=new Intent(KayitEkrani.this, AnaEkran.class);
                startActivity(topage);
            }
        });



    }
}