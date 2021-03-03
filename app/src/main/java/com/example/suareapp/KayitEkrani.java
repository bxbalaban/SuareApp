package com.example.suareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class KayitEkrani extends AppCompatActivity {
private Button button3;
public TextView textView3_adsoyad;
public TextView editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekrani);

        Button button3=(Button)findViewById(R.id.button3);
        TextView textView3_adsoyad=(TextView)findViewById(R.id.textView3_adsoyad);
        TextView editTextPhone=(TextView)findViewById(R.id.editTextPhone);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage=new Intent(KayitEkrani.this, AnaEkran.class);
                startActivity(topage);
            }
        });
        String name=textView3_adsoyad.toString();
        String phone=editTextPhone.toString();


    }
}