package com.example.suareapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suareapp.R;
import com.example.suareapp.adapters.UserAdapter2;
import com.example.suareapp.adapters.UserAdapters;
import com.example.suareapp.firebase.Constants;
import com.example.suareapp.firebase.PreferenceManager;
import com.example.suareapp.listener.UsersListener;
import com.example.suareapp.listener.UsersListener2;
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

public class Location_Person extends AppCompatActivity implements UsersListener2 {

    private PreferenceManager preferenceManager;
    private List<User> users;
    private List<String> contactsList;
    private UserAdapter2 userAdapter2;
    private TextView textErrorMessage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView imagelocation;
    private Button currentLocation;

    private int REQUEST_CODE_BATTERY_OPT=6;




    Map<String, String> namePhoneMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location__person);

        preferenceManager = new PreferenceManager(getApplicationContext());
        imagelocation=findViewById(R.id.imagelocation);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);


        RecyclerView usersRecycleView = findViewById(R.id.usersRecycleView);
        textErrorMessage = findViewById(R.id.textErrorMessage);



        users = new ArrayList<>();
        userAdapter2 = new UserAdapter2(users, (UsersListener) this);
        usersRecycleView.setAdapter(userAdapter2);

        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::getUsers);

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent current=new Intent(Location_Person.this, MapsActivity.class);
                startActivity(current);
            }
        });

        getUsers();
    }

    private void getUsers() {
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



                    }

                    if (users.size() > 0) {
                        userAdapter2.notifyDataSetChanged();
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
    public void shareLocation(User user) {
        if(user.token == null || user.token.trim().isEmpty()){
            Toast.makeText(
                    this,
                    user.name + " adlı kullanıcıya konum gönderilemiyor ",
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

    private void checkForBatteryOpt(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            PowerManager powerManager=(PowerManager) getSystemService(POWER_SERVICE);
            if(!powerManager.isIgnoringBatteryOptimizations(getPackageName())){
                AlertDialog.Builder builder=new AlertDialog.Builder(Location_Person.this);
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