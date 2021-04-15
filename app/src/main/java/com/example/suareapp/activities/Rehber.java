package com.example.suareapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.suareapp.R;
import com.example.suareapp.adapters.UserAdapters;
import com.example.suareapp.firebase.Constants;
import com.example.suareapp.firebase.PreferenceManager;
import com.example.suareapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Rehber extends AppCompatActivity {

    private PreferenceManager preferenceManager;
    private List<User> users;
    private List<User> contactsList;
    private UserAdapters userAdapters;
    private TextView textErrorMessage;
    private ProgressBar usersProggressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rehber);
        preferenceManager =new PreferenceManager(getApplicationContext());

        RecyclerView usersRecycleView = findViewById(R.id.usersRecycleView);
        textErrorMessage=findViewById(R.id.textErrorMessage);
        usersProggressBar=findViewById(R.id.usersProggressBar);

        users=new ArrayList<>();
        userAdapters=new UserAdapters(users);
        usersRecycleView.setAdapter(userAdapters);


        getUsers();
    }
    private void getUsers(){
        usersProggressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore database=FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTIONS_USERS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                usersProggressBar.setVisibility(View.GONE);
                String myUserID=preferenceManager.getString(Constants.KEY_USER_ID);
                if(task.isSuccessful()&&task.getResult()!=null){
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        //i will not be listed as a user
                        if(myUserID.equals(documentSnapshot.getId())) continue;
                        User user=new User();
                        user.name=documentSnapshot.getString(Constants.KEY_NAME);
                        user.mobile=documentSnapshot.getString(Constants.KEY_PHONE);
                        user.token=documentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                        users.add(user);
                    }

                    if(users.size()>0){
                        userAdapters.notifyDataSetChanged();
                    }
                    else{
                        textErrorMessage.setText(String.format("%s","Kullanıcılar bulunamadı"));
                        textErrorMessage.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    textErrorMessage.setText(String.format("%s","Kullanıcılar bulunamadı"));
                    textErrorMessage.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}