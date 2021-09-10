package com.example.suareapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.suareapp.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.Locale;

import timber.log.Timber;

public class KolayOku extends AppCompatActivity {

    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    Button texttovoicebutton;
    final int RequestCameraPermissionID = 1001;
    TextRecognizer textRecognizer;
    TextToSpeech tvoice;
    String data;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RequestCameraPermissionID:
            {
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kolay_oku);

        cameraView = (SurfaceView) findViewById(R.id.camera_field);
        textView=(TextView)findViewById(R.id.textfield2);
        texttovoicebutton=(Button)findViewById(R.id.voicebutton);

        textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        tvoice = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status !=TextToSpeech.ERROR)
                    tvoice.setLanguage(new Locale("tr-TR"));
            }
        });

        if (!textRecognizer.isOperational()) {
            Timber.tag("KolayOku").w("Detektör Bağımlılıkları Henüz Hazır Değil");

        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();

            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(@NonNull SurfaceHolder holder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(KolayOku.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
                @Override
                public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                    cameraSource.stop();

                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(@NonNull Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> item =detections.getDetectedItems();
                    if(item.size() !=0)
                    {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {

                                StringBuilder stringBuilder=new StringBuilder();
                                for(int i=0;i<item.size();++i){
                                    TextBlock items=item.valueAt(i);
                                    stringBuilder.append(items.getValue());
                                    stringBuilder.append("\n");
                                }
                                textView.setText(stringBuilder.toString());
                                data = stringBuilder.toString();


                            }
                        });

                    }
                }
            });
        }
        texttovoicebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextToVoice(data);

            }
        });

    }
    private void TextToVoice(String data) {
        tvoice.speak(data,TextToSpeech.QUEUE_FLUSH,null);

    }
}