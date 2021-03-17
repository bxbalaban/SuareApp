package com.example.suareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class KayitEkrani extends AppCompatActivity {
private Button get_otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekrani);

        final EditText inputMobile=findViewById(R.id.inputMobile);


        Button get_otp=(Button)findViewById(R.id.get_otp);
        final ProgressBar progressBar=findViewById(R.id.progressBar);


        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputMobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(KayitEkrani.this,"Telefon numaranızı girin lütfen",Toast.LENGTH_SHORT ).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                get_otp.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+90" + inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,

                        KayitEkrani.this,

                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                progressBar.setVisibility(View.GONE);
                                get_otp.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                progressBar.setVisibility(View.GONE);
                                get_otp.setVisibility(View.VISIBLE);
                                Toast.makeText(KayitEkrani.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String OnayID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                progressBar.setVisibility(View.GONE);
                                get_otp.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getApplicationContext(), Otp_onay.class);
                                intent.putExtra("mobile",inputMobile.getText().toString());
                                intent.putExtra("OnayID",OnayID);
                                startActivity(intent);
                            }
                        }

                );
//intent normalde buradaydı


            }
        });


    }
}