package com.example.suareapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.suareapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class KayitEkrani extends AppCompatActivity {
    private Button get_otp;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekrani);

        final EditText text_nameInput = findViewById(R.id.text_nameInput);
        final EditText inputMobile = findViewById(R.id.inputMobile);




        inputMobile.addTextChangedListener(new TextWatcher() {

            final static String DELIMITER = " ";
            String lastChar;


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int digits = inputMobile.getText().toString().length();
                if (digits > 1)
                    lastChar = inputMobile.getText().toString().substring(digits - 1);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int digits = inputMobile.getText().length();

                // prevent input dash by user
                if (digits > 0 && digits != 4 && digits != 8) {
                    CharSequence last = s.subSequence(digits - 1, digits);
                    if (last.toString().equals(DELIMITER))
                        inputMobile.getText().delete(digits - 1, digits);
                }
                // inset and remove dash
                if (digits == 3 || digits == 7) {

                    if (!lastChar.equals(DELIMITER))
                        inputMobile.append(" "); // insert a dash
                    else
                        inputMobile.getText().delete(digits - 1, digits); // delete last digit with a dash
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Button get_otp = (Button) findViewById(R.id.get_otp);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        TelephonyManager tMgr = (TelephonyManager) KayitEkrani.this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat
                    .requestPermissions(
                            KayitEkrani.this,
                            new String[] { Manifest.permission.READ_PHONE_NUMBERS,Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS },
                            9);
            return;
        }
        //otomatik numara çekme
       // @SuppressLint("HardwareIds") String mPhoneNumber = tMgr.getLine1Number();
        //inputMobile.setText(mPhoneNumber);


        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Otp_onay.class);

                if(inputMobile.getText().toString().trim().isEmpty() || text_nameInput.getText().toString().trim().isEmpty()){
                    Toast.makeText(KayitEkrani.this,"Telefon numaranızı ve isminizi girin lütfen",Toast.LENGTH_SHORT ).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                get_otp.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+90" + inputMobile.getText().toString().replace(" ",""),
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


                                intent.putExtra("mobile",inputMobile.getText().toString().replace(" ",""));
                                intent.putExtra("OnayID",OnayID);
                                intent.putExtra("name",text_nameInput.getText().toString());
                                startActivity(intent);

                            }
                        }

                );
//intent normalde buradaydı


            }
        });




//rastgele kullanıcı ekle
        /*
        FirebaseFirestore database= FirebaseFirestore.getInstance();
        HashMap<String,Object> user =new HashMap<>();
        user.put("first_name","John");
        user.put("last_name","Doe");
        database.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference -> Toast.makeText(KayitEkrani.this,"başarılı kayıt" ,Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(KayitEkrani.this,"başarısız kayıt" ,Toast.LENGTH_SHORT).show());
*/
    }


}