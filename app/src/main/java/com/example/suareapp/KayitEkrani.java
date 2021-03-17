package com.example.suareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class KayitEkrani extends AppCompatActivity {
private Button get_otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekrani);

        final EditText inputMobile=findViewById(R.id.inputMobile);


        Button get_otp=(Button)findViewById(R.id.get_otp);

        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputMobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(KayitEkrani.this,"Telefon numaranızı girin lütfen",Toast.LENGTH_SHORT ).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), Otp_onay.class);
                intent.putExtra("mobile",inputMobile.getText().toString());
                startActivity(intent);

            }
        });


    }
}