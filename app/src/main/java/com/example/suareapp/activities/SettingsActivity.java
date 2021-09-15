package com.example.suareapp.activities;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.suareapp.R;
import com.example.suareapp.firebase.Constants;
import com.example.suareapp.firebase.PreferenceManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Map;

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

        TextView textGetLoc=(TextView)findViewById(R.id.textGetLoc);
        Button getloc = (Button) findViewById(R.id.getloc);

        preferenceManager =new PreferenceManager(getApplicationContext());

        //loc
        getloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                database.collection(Constants.KEY_COLLECTIONS_USERS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    private QueryDocumentSnapshot documentSnapshot;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        myUserID = "H5TQvcuG7AXJFvlBfXmp";
                        if (task.isSuccessful() && task.getResult() != null) {

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                //i will not be listed as a user
                                if (myUserID.equals(documentSnapshot.getId())) {
                                    String locationString= documentSnapshot.get("location").toString();
                                    Map<String, Object> myMap = (Map<String, Object>) documentSnapshot.get("location");
                                    double latitude =Double.parseDouble(myMap.get("latitude").toString());
                                    double longitude = Double.parseDouble(myMap.get("longitude").toString());

                                    LatLng location= new LatLng(latitude,longitude);

                                    textGetLoc.setText(location+"");

                                    /*Matcher matcher = Pattern.compile("latitude(.*?),").matcher(locationString);

                                    while (matcher.find()) {
                                        System.out.println(matcher.group());
                                        //textGetLoc.setText(matcher.group());
                                    }

                                    Matcher matcher2 = Pattern.compile("longitude(.*?)").matcher(locationString);

                                    while (matcher.find()) {
                                        System.out.println(matcher2.group());
                                        textGetLoc.setText(matcher2.group());
                                    }
                                */
                                }
                            }
                        }
                    }
                });
            }
        });


        //loc

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

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setCancelable(true);
                builder.setTitle("DİKKAT !");
                builder.setMessage("HESABINIZ KALICI OLARAK SİLİNECEK ONAYLIYOR MUSUNUZ?");
                builder.setPositiveButton("HESABIMI SİL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database.collection(Constants.KEY_COLLECTIONS_USERS).document(myUserID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SettingsActivity.this, "Hesabınız Kalıcı Olarak Silindi", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SettingsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comments.getText().length()!=0){
                    preferenceManager=new PreferenceManager(getApplicationContext());
                    //System.out.println("userID ana ekran + "+preferenceManager.getString(Constants.KEY_USER_ID));
                    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if(task.isSuccessful()&&task.getResult()!=null){
                                sendCommentToDatabase(comments.getText().toString());
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(SettingsActivity.this, "Gönderim Sağlanamadı Lütfen Tekrar Deneyin", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
    private void sendCommentToDatabase(String comment){
        FirebaseFirestore database =FirebaseFirestore.getInstance();
        DocumentReference documentReference=database.collection(Constants.KEY_COLLECTIONS_USERS).document(
                preferenceManager.getString(Constants.KEY_USER_ID)
        );
        documentReference.update(Constants.KEY_COMMENT,comment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SettingsActivity.this,"Teşekkür Ederiz :) ",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(AnaEkran.this,"Unable Token update " +e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

}