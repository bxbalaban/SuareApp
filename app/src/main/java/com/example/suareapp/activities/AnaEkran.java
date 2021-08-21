package com.example.suareapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.suareapp.R;
import com.example.suareapp.firebase.Constants;
import com.example.suareapp.firebase.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class AnaEkran extends AppCompatActivity {
    private PreferenceManager preferenceManager;
    private Button button8;
    private Button button5;
    private Button button11;
    private Button button10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_ekran2);


        Button button_suare=(Button)findViewById(R.id.button_suare);
        Button button_konum=(Button)findViewById(R.id.button_konum);
        Button button_ilac=(Button)findViewById(R.id.button_ilac);
        Button button_oyun=(Button)findViewById(R.id.button_oyun);
        Button button_kolayoku=(Button)findViewById(R.id.button_kolayoku);
        Button button_egzersiz=(Button)findViewById(R.id.button_egzersiz);

        button_konum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage1=new Intent(AnaEkran.this, KonumGiris.class);
                startActivity(topage1);
            }
        });

        button_suare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage2=new Intent(AnaEkran.this, Rehber.class);
                startActivity(topage2);
            }
        });
        button_oyun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage2=new Intent(AnaEkran.this, GameActivity.class);
                startActivity(topage2);
            }
        });
        /*button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            }
        });
*/
        preferenceManager=new PreferenceManager(getApplicationContext());
        //System.out.println("userID ana ekran + "+preferenceManager.getString(Constants.KEY_USER_ID));
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
               if(task.isSuccessful()&&task.getResult()!=null){
                   sendFCMTokenToDatabase(task.getResult().getToken());
               }
            }
        });
    }

    private void sendFCMTokenToDatabase(String token){
        FirebaseFirestore database =FirebaseFirestore.getInstance();
        DocumentReference documentReference=database.collection(Constants.KEY_COLLECTIONS_USERS).document(
                preferenceManager.getString(Constants.KEY_USER_ID)
        );
        documentReference.update(Constants.KEY_FCM_TOKEN,token)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AnaEkran.this,"Token updated",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(AnaEkran.this,"Unable Token update " +e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //no signout

}