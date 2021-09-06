package com.example.suareapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.suareapp.R;
import com.example.suareapp.firebase.Constants;
import com.example.suareapp.firebase.PreferenceManager;
import com.example.suareapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {
    private PreferenceManager preferenceManager;
    String myUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView settings_back = (ImageView) findViewById(R.id.settings_back);
        Button save_name = (Button) findViewById(R.id.save_name);
        Button send_comment = (Button) findViewById(R.id.send_comment);
        Button delete_account = (Button) findViewById(R.id.delete_account);
        EditText new_name=(EditText)findViewById(R.id.new_name);
        EditText comments=(EditText)findViewById(R.id.comments);

        preferenceManager =new PreferenceManager(getApplicationContext());

        settings_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage1=new Intent(SettingsActivity.this, AnaEkran.class);
                startActivity(topage1);
            }
        });

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTIONS_USERS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                 myUserID = preferenceManager.getString(Constants.KEY_USER_ID);
                if (task.isSuccessful() && task.getResult() != null) {

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        //i will not be listed as a user
                        if (myUserID.equals(documentSnapshot.getId())) {
                            new_name.setText(documentSnapshot.getString(Constants.KEY_NAME));
                            preferenceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME));
                        }
                    }
                }
            }
        });

        save_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new_name.getText().toString().isEmpty() || new_name.getText().toString().length() < 5 || new_name.getText().toString().equals(preferenceManager.getString(Constants.KEY_NAME))) {
                    Toast.makeText(SettingsActivity.this, "Aynı İsim ve Boş Alanlar Kaydedilemez", Toast.LENGTH_SHORT).show();
                } else {
                    preferenceManager.putString(Constants.KEY_NAME,new_name.getText().toString());
                    database.collection(Constants.KEY_COLLECTIONS_USERS).document(myUserID).update("name", preferenceManager.getString(Constants.KEY_NAME)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(SettingsActivity.this, "İsminiz "+preferenceManager.getString(Constants.KEY_NAME)+" olarak değiştirildi.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SettingsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        });



    }
}