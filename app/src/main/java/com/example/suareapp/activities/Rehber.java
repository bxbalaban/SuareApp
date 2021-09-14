package com.example.suareapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
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
import com.google.gson.Gson;

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
    private ImageView imageConference;

    private int REQUEST_CODE_BATTERY_OPT=1;




    Map<String, String> namePhoneMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rehber);
        preferenceManager = new PreferenceManager(getApplicationContext());
        imageConference=findViewById(R.id.imageConference);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);


        RecyclerView usersRecycleView = findViewById(R.id.usersRecycleView);
        textErrorMessage = findViewById(R.id.textErrorMessage);


        users = new ArrayList<>();
        userAdapters = new UserAdapters(users, this);
        usersRecycleView.setAdapter(userAdapters);

        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::getUsers);

        getUsers();

        //checkForBatteryOpt();

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
                        user.id=documentSnapshot.getString(Constants.KEY_USER_ID);


                        users.add(user);

                        //i will not be listed as a user
                       // if (myUserID.equals(documentSnapshot.getId())) continue;
                        /*
                        for (Map.Entry<String, String> entry : namePhoneMap.entrySet()) {
                            Toast.makeText(Rehber.this, " Eentyr key "+documentSnapshot.getString(Constants.KEY_PHONE), Toast.LENGTH_SHORT).show();;
                           if(documentSnapshot.getString(Constants.KEY_PHONE).equals(entry.getKey())){
                               Toast.makeText(Rehber.this, " entyr key "+entry.getKey(), Toast.LENGTH_SHORT).show();;
                                User user = new User();
                                user.name = entry.getValue();
                                user.mobile = entry.getKey();
                                user.id=documentSnapshot.getString(Constants.KEY_USER_ID);
                                user.token = documentSnapshot.getString(Constants.KEY_FCM_TOKEN);

                                users.add(user);
                            }

                        }
                        //Toast.makeText(Rehber.this,""+documentSnapshot.getString(Constants.KEY_PHONE),Toast.LENGTH_SHORT).show();

*/


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
            Intent intent=new Intent(getApplicationContext(),OutgoingInvitationActivity.class);
            intent.putExtra("user",user);
            intent.putExtra("type","audio");
            startActivity(intent);
        }
    }

    @Override
    public void onMultipleUsersAction(Boolean isMultipleUsersSelected) {
        if(isMultipleUsersSelected){
            imageConference.setVisibility(View.VISIBLE);
            imageConference.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),OutgoingInvitationActivity.class);
                    intent.putExtra("selectedUsers",new Gson().toJson(userAdapters.getSelectedUsers()));
                    intent.putExtra("type","video");
                    intent.putExtra("isMultiple",true);
                    startActivity(intent);
                }
            });
        }
        else{
            imageConference.setVisibility(View.GONE);
        }
    }

    @Override
    public void initiateLocation(User user) {
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
            intent.putExtra("type","location");
            startActivity(intent);
        }
    }

    private void checkForBatteryOpt(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            PowerManager powerManager=(PowerManager) getSystemService(POWER_SERVICE);
            if(!powerManager.isIgnoringBatteryOptimizations(getPackageName())){
                AlertDialog.Builder builder=new AlertDialog.Builder(Rehber.this);
                builder.setTitle("Uyarı");
                builder.setMessage("Batarya Optimizasyonu Etkinleştirilsin mi?");
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                        startActivityForResult(intent,REQUEST_CODE_BATTERY_OPT);
                    }
                });
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_BATTERY_OPT){
            checkForBatteryOpt();
        }
    }
}