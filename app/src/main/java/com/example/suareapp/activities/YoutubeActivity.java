package com.example.suareapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.suareapp.R;

public class YoutubeActivity extends AppCompatActivity  {
    String level;

    String videoStr;


    public String API_KEY="";
    public String PLAY_LIST_ID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        level=getIntent().getStringExtra("level");
        ImageView video_back=(ImageView) findViewById(R.id.video_back);



        video_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage1=new Intent(YoutubeActivity.this, AnaEkran.class);
                startActivity(topage1);
            }
        });

        if (level.equals("level1")){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/playlist?list=PLJOW5FV22UbY5qgVOQObHA5JcZG_XPYnF"));
            startActivity(intent);

        }
        else if (level.equals("level2")){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/playlist?list=PLJOW5FV22UbZSTuY7gd9fJObnyOywRIZW"));
            startActivity(intent);

        }
        else if (level.equals("level3")){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/playlist?list=PLJOW5FV22UbaFIfBzAoU_oYBxV3iEFiOK"));
            startActivity(intent);

        }
        else{
            Toast.makeText(this, "Hata", Toast.LENGTH_SHORT).show();
            finish();
        }

         /*  WebView mWebView = (WebView) findViewById(R.id.YoutubeView);
        //build your own src link with your video ID
        String videoStr = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/47yJ2XCRLZs\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings ws = mWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        mWebView.loadData(videoStr, "text/html", "utf-8");*/
    }



}