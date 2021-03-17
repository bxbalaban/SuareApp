package com.example.suareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Otp_onay extends AppCompatActivity {
private EditText input_code_1,input_code_2,input_code_3,input_code_4,input_code_5,input_code_6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_onay);

        TextView textMobile= findViewById(R.id.textMobile);
        textMobile.setText(String.format(
                "+90-%s",getIntent().getStringExtra("mobile")
        ));

        input_code_1=findViewById(R.id.input_code_1);
        input_code_2=findViewById(R.id.input_code_2);
        input_code_3=findViewById(R.id.input_code_3);
        input_code_4=findViewById(R.id.input_code_4);
        input_code_5=findViewById(R.id.input_code_5);
        input_code_6=findViewById(R.id.input_code_6);

        setupOTPInputs();

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