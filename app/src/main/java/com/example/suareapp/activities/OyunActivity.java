package com.example.suareapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.suareapp.R;

import java.sql.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class OyunActivity extends AppCompatActivity {

    Button button11,button12,button13,button21,button22,button23,button31,button32,button33,button41,button42,button43,button_timer;

    Integer[] cardArray={101,102,103,104,105,106,201,202,203,204,205,206};
    int image101,image102,image103,image104,image105,image106,image201,image202,image203,image204,image205,image206;
    int firstCard,secondCard;
    int clickedFirst,clickedSecond;
    int cardNumber=1;
    boolean successful=false;
    boolean exit=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun);

        new CountDownTimer(40000, 1000) {

            public void onTick(long millisUntilFinished) {
                button_timer.setText("KALAN SÜRE : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                button_timer.setText("SÜRENİZ BİTMİŞTİR");
                Handler handler = new Handler();
                if(!successful&&!exit){
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Intent intent=new Intent(getApplicationContext(),OyunActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 3000);   //5 seconds
                }


            }
        }.start();



        button_timer=(Button) findViewById(R.id.button_timer);

        button11=(Button) findViewById(R.id.button11);
        button12=(Button) findViewById(R.id.button12);
        button13=(Button) findViewById(R.id.button13);
        button21=(Button) findViewById(R.id.button21);
        button22=(Button) findViewById(R.id.button22);
        button23=(Button) findViewById(R.id.button23);
        button31=(Button) findViewById(R.id.button31);
        button32=(Button) findViewById(R.id.button32);
        button33=(Button) findViewById(R.id.button33);
        button41=(Button) findViewById(R.id.button41);
        button42=(Button) findViewById(R.id.button42);
        button43=(Button) findViewById(R.id.button43);

        button11.setTag("0");
        button12.setTag("1");
        button13.setTag("2");
        button21.setTag("3");
        button22.setTag("4");
        button23.setTag("5");
        button31.setTag("6");
        button32.setTag("7");
        button33.setTag("8");
        button41.setTag("9");
        button42.setTag("10");
        button43.setTag("11");

        frontOfCardsRecources();
        Collections.shuffle(Arrays.asList(cardArray));

        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard=Integer.parseInt((String) v.getTag());
                game(button11,theCard);
            }
        });
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard=Integer.parseInt((String) v.getTag());
                game(button12,theCard);
            }
        });
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard=Integer.parseInt((String) v.getTag());
                game(button13,theCard);
            }
        });
        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard=Integer.parseInt((String) v.getTag());
                game(button21,theCard);
            }
        });
        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard=Integer.parseInt((String) v.getTag());
                game(button22,theCard);
            }
        });
        button23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard=Integer.parseInt((String) v.getTag());
                game(button23,theCard);
            }
        });
        button31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard=Integer.parseInt((String) v.getTag());
                game(button31,theCard);
            }
        });
        button32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard=Integer.parseInt((String) v.getTag());
                game(button32,theCard);
            }
        });
        button33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard=Integer.parseInt((String) v.getTag());
                game(button33,theCard);
            }
        });
        button41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard=Integer.parseInt((String) v.getTag());
                game(button41,theCard);
            }
        });
        button42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard=Integer.parseInt((String) v.getTag());
                game(button42,theCard);
            }
        });
        button43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard=Integer.parseInt((String) v.getTag());
                game(button43,theCard);
            }
        });


    }
    private void frontOfCardsRecources(){
        image101=R.drawable.ic_card_chef;
        image102=R.drawable.ic_card_detective;
        image103=R.drawable.ic_card_king;
        image104=R.drawable.ic_card_nurse;
        image105=R.drawable.ic_card_policewoman;
        image106=R.drawable.ic_card_queen;
        image201=R.drawable.ic_card_boy;
        image202=R.drawable.ic_card_girl;
        image203=R.drawable.ic_card_news;
        image204=R.drawable.ic_card_pill;
        image205=R.drawable.ic_card_singer;
        image206=R.drawable.ic_card_fire;

    }
    private void game(Button button, int card){
        //set the correct image to the button

        if(cardArray[card]==101){
            button.setBackgroundResource(image101);
        }
        else if(cardArray[card]==102){
            button.setBackgroundResource(image102);
        } else if(cardArray[card]==103){
            button.setBackgroundResource(image103);
        } else if(cardArray[card]==104){
            button.setBackgroundResource(image104);
        } else if(cardArray[card]==105){
            button.setBackgroundResource(image105);
        } else if(cardArray[card]==106){
            button.setBackgroundResource(image106);
        } else if(cardArray[card]==201){
            button.setBackgroundResource(image101);
        } else if(cardArray[card]==202){
            button.setBackgroundResource(image102);
        } else if(cardArray[card]==203){
            button.setBackgroundResource(image103);
        } else if(cardArray[card]==204){
            button.setBackgroundResource(image104);
        } else if(cardArray[card]==205){
            button.setBackgroundResource(image105);
        } else if(cardArray[card]==206){
            button.setBackgroundResource(image106);
        }
        //check matching cards and save temp

        if(cardNumber ==1 ){
            firstCard=cardArray[card];
            if (firstCard>200){
                firstCard=firstCard-100;
            }
            cardNumber=2;
            clickedFirst=card;
            button.setEnabled(false);
        }
        else if(cardNumber==2){
            secondCard=cardArray[card];
            if (secondCard>200){
                secondCard=secondCard-100;
            }
            cardNumber=1;
            clickedSecond=card;

            button11.setEnabled(false);
            button12.setEnabled(false);
            button13.setEnabled(false);
            button21.setEnabled(false);
            button22.setEnabled(false);
            button23.setEnabled(false);
            button31.setEnabled(false);
            button32.setEnabled(false);
            button33.setEnabled(false);
            button41.setEnabled(false);
            button42.setEnabled(false);
            button43.setEnabled(false);

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //check are selected ones equal
                    calculate();
                }
            },1000);
        }
    }
    private void calculate(){
        //if cards are matching show them
        if (firstCard==secondCard){
            if (clickedFirst==0){
                button11.setVisibility(View.INVISIBLE);
            }
            else if (clickedFirst==1){
                button12.setVisibility(View.INVISIBLE);
            }else if (clickedFirst==2){
                button13.setVisibility(View.INVISIBLE);
            }else if (clickedFirst==3){
                button21.setVisibility(View.INVISIBLE);
            }else if (clickedFirst==4){
                button22.setVisibility(View.INVISIBLE);
            }else if (clickedFirst==5){
                button23.setVisibility(View.INVISIBLE);
            }else if (clickedFirst==6){
                button31.setVisibility(View.INVISIBLE);
            }else if (clickedFirst==7){
                button32.setVisibility(View.INVISIBLE);
            }else if (clickedFirst==8){
                button33.setVisibility(View.INVISIBLE);
            }else if (clickedFirst==9){
                button41.setVisibility(View.INVISIBLE);
            }else if (clickedFirst==10){
                button42.setVisibility(View.INVISIBLE);
            }else if (clickedFirst==11){
                button43.setVisibility(View.INVISIBLE);
            }
            if (clickedSecond==0){
                button11.setVisibility(View.INVISIBLE);
            }
            else if (clickedSecond==1){
                button12.setVisibility(View.INVISIBLE);
            }else if (clickedSecond==2){
                button13.setVisibility(View.INVISIBLE);
            }else if (clickedSecond==3){
                button21.setVisibility(View.INVISIBLE);
            }else if (clickedSecond==4){
                button22.setVisibility(View.INVISIBLE);
            }else if (clickedSecond==5){
                button23.setVisibility(View.INVISIBLE);
            }else if (clickedSecond==6){
                button31.setVisibility(View.INVISIBLE);
            }else if (clickedSecond==7){
                button32.setVisibility(View.INVISIBLE);
            }else if (clickedSecond==8){
                button33.setVisibility(View.INVISIBLE);
            }else if (clickedSecond==9){
                button41.setVisibility(View.INVISIBLE);
            }else if (clickedSecond==10){
                button42.setVisibility(View.INVISIBLE);
            }else if (clickedSecond==11){
                button43.setVisibility(View.INVISIBLE);
            }

        }
        else{
            button11.setBackgroundResource(R.drawable.ic_card_anonimous);
            button12.setBackgroundResource(R.drawable.ic_card_anonimous);
            button13.setBackgroundResource(R.drawable.ic_card_anonimous);
            button21.setBackgroundResource(R.drawable.ic_card_anonimous);
            button22.setBackgroundResource(R.drawable.ic_card_anonimous);
            button23.setBackgroundResource(R.drawable.ic_card_anonimous);
            button31.setBackgroundResource(R.drawable.ic_card_anonimous);
            button32.setBackgroundResource(R.drawable.ic_card_anonimous);
            button33.setBackgroundResource(R.drawable.ic_card_anonimous);
            button41.setBackgroundResource(R.drawable.ic_card_anonimous);
            button42.setBackgroundResource(R.drawable.ic_card_anonimous);
            button43.setBackgroundResource(R.drawable.ic_card_anonimous);
        }
        button11.setEnabled(true);
        button12.setEnabled(true);
        button13.setEnabled(true);
        button21.setEnabled(true);
        button22.setEnabled(true);
        button23.setEnabled(true);
        button31.setEnabled(true);
        button32.setEnabled(true);
        button33.setEnabled(true);
        button41.setEnabled(true);
        button42.setEnabled(true);
        button43.setEnabled(true);

        //check if the game is over
        checkEnd();
    }

    private void  checkEnd(){
        if(button11.getVisibility()==View.INVISIBLE &&
                button11.getVisibility()==View.INVISIBLE &&
                button12.getVisibility()==View.INVISIBLE &&
                button13.getVisibility()==View.INVISIBLE &&
                button21.getVisibility()==View.INVISIBLE &&
                button22.getVisibility()==View.INVISIBLE &&
                button23.getVisibility()==View.INVISIBLE &&
                button31.getVisibility()==View.INVISIBLE &&
                button32.getVisibility()==View.INVISIBLE &&
                button33.getVisibility()==View.INVISIBLE &&
                button41.getVisibility()==View.INVISIBLE &&
                button42.getVisibility()==View.INVISIBLE &&
                button43.getVisibility()==View.INVISIBLE
        ){
           /* AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(OyunActivity.this);
            alertDialogBuilder.setMessage("Oyun Bitti Tebrikler!!")
                    .setCancelable(true)
                    .setPositiveButton(" TEKRAR ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(getApplicationContext(),OyunActivity.class);
                                    startActivity(intent);
                                    finish();
                        }
                    }).setNegativeButton(" ÇIK ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();*/
            successful=true;
            button_timer.setText("TEBRİKLER ");
            Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent=new Intent(getApplicationContext(),OyunActivity.class);
                        if(!exit)startActivity(intent);
                        finish();
                    }
                }, 1000);   //5 seconds

        }
    }
    public void onBackPressed(){
        exit=true;
        finish();
    }


}