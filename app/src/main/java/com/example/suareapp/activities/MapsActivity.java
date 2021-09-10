package com.example.suareapp.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.suareapp.R;
import com.example.suareapp.firebase.Constants;
import com.example.suareapp.firebase.PreferenceManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.protobuf.DescriptorProtos;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager manager;
    private final int MIN_TIME = 1000;
    private final int MIN_DISTANCE = 1;
    private Marker myMarker;
    private Location location;
    private FirebaseFirestore mFireStore;
    private FirebaseAuth mFireAuth;
    private DatabaseReference mDatabase;
    private static final String TAG = null;
    private PreferenceManager preferenceManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    LatLng userLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mFireAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        preferenceManager = new PreferenceManager(getApplicationContext());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    sendLocationToDatabase(location);
                }
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();

        getLocation();
        //onLocationChanged(location);
        //LatLng userLocation=new LatLng(38.0168841,32.5054043);
        //LatLng userLocation=new LatLng(location.getLatitude(),location.getLongitude());
        //myMarker = mMap.addMarker(new MarkerOptions().position(userLocation).title("Buradayim"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,16));
        getUpdateLocation();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location=task.getResult();
                if (location!=null){
                    Geocoder geocoder=new Geocoder(MapsActivity.this,Locale.getDefault());
                    userLocation= new LatLng(location.getLatitude(),location.getLongitude());
                    myMarker = mMap.addMarker(new MarkerOptions().position(userLocation).title("Buradayim"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,16));

                }
            }
        });
    }


    //Konum için izin alınıyor.
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUpdateLocation();
            } else {
                Toast.makeText(this, "İzin Verilmesi Gereklidir.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    // Firestoredan lokasyon çekiliyor.
    private void readChange() {
        Toast.makeText(MapsActivity.this, "read içinde", Toast.LENGTH_SHORT).show();
        mFireStore.collection("users").document(preferenceManager.getString(Constants.KEY_USER_ID))
                .get(Source.valueOf(preferenceManager.getString(Constants.KEY_LOCATION)))
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {


                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot.exists()) {
                                try {
                                    MyLocation newlocation = (MyLocation) snapshot.get(preferenceManager.getString(Constants.KEY_LOCATION));
                                    MyLocation location = (MyLocation) snapshot.get(String.valueOf(MyLocation.class));
                                    if (location != null) {
                                        myMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }


                });
    }

    // Firestoreda konum güncellemesi yapıyor.
    private void getUpdateLocation() {
        if (manager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                } else {
                    Toast.makeText(this, "Sağlayıcı Etkinleştirilemedi.", Toast.LENGTH_SHORT).show();
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        }
    }


 // Konum veri tabanına kayıt ediliyor.

    public void sendLocationToDatabase(Location location){
        FirebaseFirestore database =FirebaseFirestore.getInstance();
        DocumentReference documentReference=database.collection(Constants.KEY_COLLECTIONS_USERS).document(
                preferenceManager.getString(Constants.KEY_USER_ID)
        );
        documentReference.update(Constants.KEY_LOCATION,location)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MapsActivity.this,"Location 2 updated",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(AnaEkran.this,"Unable Token update " +e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(location != null){
            sendLocationToDatabase(location);
        }
        else{
            Toast.makeText(this,"Lokasyon Bulunamadı.",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


}
