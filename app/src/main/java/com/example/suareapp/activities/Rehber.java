package com.example.suareapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suareapp.R;
import com.example.suareapp.adapters.UserAdapters;
import com.example.suareapp.firebase.Constants;
import com.example.suareapp.firebase.PreferenceManager;
import com.example.suareapp.listener.UsersListener;
import com.example.suareapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rehber extends AppCompatActivity implements UsersListener {

    private PreferenceManager preferenceManager;
    private List<User> users;
    private List<String> contactsList;
    private UserAdapters userAdapters;
    private TextView textErrorMessage;
    private SwipeRefreshLayout swipeRefreshLayout;



    Map<String, String> namePhoneMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rehber);
        preferenceManager = new PreferenceManager(getApplicationContext());

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);


        RecyclerView usersRecycleView = findViewById(R.id.usersRecycleView);
        textErrorMessage = findViewById(R.id.textErrorMessage);


        users = new ArrayList<>();
        userAdapters = new UserAdapters(users, this);
        usersRecycleView.setAdapter(userAdapters);

        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::getUsers);

        getUsers();

    }

    public void getContactList() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        namePhoneMap.put(phoneNo,name);


                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }


    private void getUsers() {

        getContactList();
        swipeRefreshLayout.setRefreshing(true);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTIONS_USERS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                swipeRefreshLayout.setRefreshing(false);

                String myUserID = preferenceManager.getString(Constants.KEY_USER_ID);
                if (task.isSuccessful() && task.getResult() != null) {
                    users.clear();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        //i will not be listed as a user
                        if (myUserID.equals(documentSnapshot.getId())) continue;

                        User user = new User();
                        user.name = documentSnapshot.getString(Constants.KEY_NAME);
                        user.mobile = documentSnapshot.getString(Constants.KEY_PHONE);
                        user.token = documentSnapshot.getString(Constants.KEY_FCM_TOKEN);

                        users.add(user);
                        /*//i will not be listed as a user
                       // if (myUserID.equals(documentSnapshot.getId())) continue;
                        for (Map.Entry<String, String> entry : namePhoneMap.entrySet()) {

                           if(documentSnapshot.getString(Constants.KEY_PHONE).equals(entry.getKey())){
                                User user = new User();
                                user.name = entry.getValue();
                                user.mobile = entry.getKey();
                                user.token = documentSnapshot.getString(Constants.KEY_FCM_TOKEN);

                                users.add(user);
                            }

                        }
                        Toast.makeText(Rehber.this,""+documentSnapshot.getString(Constants.KEY_PHONE),Toast.LENGTH_SHORT).show();


                    }*/

                    }

                    if (users.size() > 0) {
                        userAdapters.notifyDataSetChanged();
                    }
                    else {
                        textErrorMessage.setText(String.format("%s", "Kullanıcılar bulunamadı"));
                        textErrorMessage.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    textErrorMessage.setText(String.format("%s", "Kullanıcılar bulunamadı"));
                    textErrorMessage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void initiateVideoMeeting(User user) {
        if(user.token == null || user.token.trim().isEmpty()){
            Toast.makeText(
                    this,
                    user.name + " adlı kullanıcıya ulaşılamıyor ",
                    Toast.LENGTH_LONG
            ).show();
        }
        else{
            Intent intent=new Intent(getApplicationContext(),OutgoingInvitationActivity.class);
            intent.putExtra("user",user);
            intent.putExtra("type","video");
            startActivity(intent);
        }
    }

    @Override
    public void initiateAudioMeeting(User user) {
        if(user.token == null || user.token.trim().isEmpty()){
            Toast.makeText(
                    this,
                    user.name + " adlı kullanıcıya ulaşılamıyor ",
                    Toast.LENGTH_LONG
            ).show();
        }
        else{
            Toast.makeText(
                    this,
                    user.name + " Sesli aranıyor ",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}