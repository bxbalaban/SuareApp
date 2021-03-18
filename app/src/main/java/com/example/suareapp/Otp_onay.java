package com.example.suareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.net.Inet4Address;
import java.util.concurrent.TimeUnit;

public class Otp_onay extends AppCompatActivity {
private EditText input_code_1,input_code_2,input_code_3,input_code_4,input_code_5,input_code_6;
private String textOnayID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_onay);

        TextView textMobile= findViewById(R.id.textMobile);
        textMobile.setText(String.format(
                "+90%s",getIntent().getStringExtra("mobile")
        ));

        input_code_1=findViewById(R.id.input_code_1);
        input_code_2=findViewById(R.id.input_code_2);
        input_code_3=findViewById(R.id.input_code_3);
        input_code_4=findViewById(R.id.input_code_4);
        input_code_5=findViewById(R.id.input_code_5);
        input_code_6=findViewById(R.id.input_code_6);

        setupOTPInputs();

        final ProgressBar progressBar2=findViewById(R.id.progressBar2);
        final Button onay_otp=findViewById(R.id.onay_otp);

        textOnayID=getIntent().getStringExtra("OnayID");

        onay_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(input_code_1.getText().toString().trim().isEmpty()
                 ||input_code_2.getText().toString().trim().isEmpty()
                 ||input_code_3.getText().toString().trim().isEmpty()
                 ||input_code_4.getText().toString().trim().isEmpty()
                 ||input_code_5.getText().toString().trim().isEmpty()
                 ||input_code_6.getText().toString().trim().isEmpty()){

                    Toast.makeText(Otp_onay.this,"gönderilen şifreyi girmelisiniz",Toast.LENGTH_SHORT).show();
                    return;
                }

                String password=input_code_1.getText().toString()+
                                input_code_2.getText().toString()+
                                input_code_3.getText().toString()+
                                input_code_4.getText().toString()+
                                input_code_5.getText().toString()+
                                input_code_6.getText().toString();

                if(textOnayID!=null){
                    progressBar2.setVisibility(View.VISIBLE);
                    onay_otp.setVisibility(View.INVISIBLE);

                    PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(textOnayID,password);

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressBar2.setVisibility(View.GONE);
                            onay_otp.setVisibility(View.VISIBLE);

                            if(task.isSuccessful()){
                                Intent intent =new Intent(getApplicationContext(),AnaEkran.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                FirebaseUser user = task.getResult().getUser();
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Otp_onay.this,"Onay kodu yanlış",Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                }

            }
        });

        findViewById(R.id.text_yeniden).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+90" + getIntent().getStringExtra("Mobile"),
                        60,
                        TimeUnit.SECONDS,

                        Otp_onay.this,

                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(Otp_onay.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String yeniOnayID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                textOnayID=yeniOnayID;
                                Toast.makeText(Otp_onay.this,"tekrar şifre gönderildi",Toast.LENGTH_SHORT).show();


                            }
                        }

                );
            }
        });
    }

    private void setupOTPInputs(){
        input_code_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                    input_code_2.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_code_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                    input_code_3.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_code_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                    input_code_4.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_code_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                    input_code_5.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_code_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty())
                    input_code_6.requestFocus();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}