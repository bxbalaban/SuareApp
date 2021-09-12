package com.example.suareapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suareapp.R;

public class YoutubeActivity extends AppCompatActivity  {
    String level;
    WebView mWebView;
    String videoStr;


    public String API_KEY="";
    public String PLAY_LIST_ID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        level=getIntent().getStringExtra("level");
        ImageView video_back=(ImageView) findViewById(R.id.video_back);
        Button video_breathe=(Button)findViewById(R.id.video_breathe);
        Button video_next=(Button)findViewById(R.id.video_next);
        Button video_before=(Button)findViewById(R.id.video_before);







        mWebView = (WebView) findViewById(R.id.YoutubeView);


        video_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent topage1=new Intent(YoutubeActivity.this, AnaEkran.class);
                startActivity(topage1);
            }
        });

        if (level.equals("level1")){


            //build your own src link with your video ID
             videoStr = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/47yJ2XCRLZs\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            WebSettings ws = mWebView.getSettings();
            ws.setJavaScriptEnabled(true);
            mWebView.loadData(videoStr, "text/html", "utf-8");



            video_breathe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     videoStr = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/47yJ2XCRLZs\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

                    mWebView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            return false;
                        }
                    });
                    WebSettings ws = mWebView.getSettings();
                    ws.setJavaScriptEnabled(true);
                    mWebView.loadData(videoStr, "text/html", "utf-8");
                }
            });


        }
        else if (level.equals("level2")){

            videoStr = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/47yJ2XCRLZs\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            WebSettings ws = mWebView.getSettings();
            ws.setJavaScriptEnabled(true);
            mWebView.loadData(videoStr, "text/html", "utf-8");

            video_breathe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //build your own src link with your video ID
                     videoStr = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/De6rFH8oKsI\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

                    mWebView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            return false;
                        }
                    });
                    WebSettings ws = mWebView.getSettings();
                    ws.setJavaScriptEnabled(true);
                    mWebView.loadData(videoStr, "text/html", "utf-8");

                }
            });
        }
        else if (level.equals("level3")){

            videoStr = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/47yJ2XCRLZs\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            WebSettings ws = mWebView.getSettings();
            ws.setJavaScriptEnabled(true);
            mWebView.loadData(videoStr, "text/html", "utf-8");

            video_breathe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //build your own src link with your video ID
                     videoStr = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/h8_dFMwFA4A\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

                    mWebView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            return false;
                        }
                    });
                    WebSettings ws = mWebView.getSettings();
                    ws.setJavaScriptEnabled(true);
                    mWebView.loadData(videoStr, "text/html", "utf-8");
                }
            });
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
    public void onCompletion(WebView mp) {
        finish(); //This will end the current activity
    }
    public void pause(){
        //NOT videoview.pause(); Needn't save Stop position
        if (mp != null){
            mp.pause();
        }
    }

    public void resume(){
        //NOT videoview.resume();
        if (mp != null){
            mp.start(); //Video will begin where it stopped
        }
    }

}