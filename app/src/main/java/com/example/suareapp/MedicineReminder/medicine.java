package com.example.suareapp.MedicineReminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.suareapp.R;
import com.facebook.react.modules.timepicker.TimePickerDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class medicine extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Button ac,ac2,ac3,ac4;
    Button tok,tok2,tok3,tok4;
    EditText m_ad,m_ad2,m_ad3,m_ad4;
    Button s,s2,s3,s4;
    Button ö,ö2,ö3,ö4;
    Button a,a2,a3,a4;
    EditText ilac_adı;

    int hour;
    int minute;

    boolean iscolor = true;
    long timeInMs;
    DialogFragment timePicker;
    AlarmManager alarmManager;
    boolean fs1=false;
    Calendar c;
    int cs=0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        Intent ilac=new Intent(medicine.this, AlarmField.class);
        ilac_adı=(EditText)findViewById(R.id.comments);

        //startActivity(ilac);



        ac=(Button) findViewById(R.id.AC);
        ac2=(Button) findViewById(R.id.AC2);
        ac3=(Button) findViewById(R.id.AC3);
        ac4=(Button) findViewById(R.id.AC4);

        tok=(Button) findViewById(R.id.TOK);
        tok2=(Button) findViewById(R.id.TOK2);
        tok3=(Button) findViewById(R.id.TOK3);
        tok4=(Button) findViewById(R.id.TOK4);

        m_ad=(EditText) findViewById(R.id.comments);
        m_ad2=(EditText) findViewById(R.id.comments2);
        m_ad3=(EditText) findViewById(R.id.comments3);
        m_ad4=(EditText) findViewById(R.id.comments4);

        s=(Button) findViewById(R.id.SABAH);
        s2=(Button) findViewById(R.id.SABAH2);
        s3=(Button) findViewById(R.id.SABAH3);
        s4=(Button) findViewById(R.id.SABAH4);

        ö=(Button) findViewById(R.id.ÖĞLE);
        ö2=(Button) findViewById(R.id.ÖĞLE2);
        ö3=(Button) findViewById(R.id.ÖĞLE3);
        ö4=(Button) findViewById(R.id.ÖĞLE4);

        a=(Button) findViewById(R.id.AKŞAM);
        a2=(Button) findViewById(R.id.AKŞAM2);
        a3=(Button) findViewById(R.id.AKŞAM3);
        a4=(Button) findViewById(R.id.AKŞAM4);



        ac.setOnClickListener(new DoubleClickLister() {
            @Override
            protected void onSingleClick(View v) {
                ac.setBackgroundColor(Color.GREEN);

            }

            @Override
            protected void onDoubleClick(View v) {
                ac.setBackgroundColor(R.drawable.background_button6);


            }
        });

        tok.setOnClickListener(new DoubleClickLister() {
            @Override
            protected void onSingleClick(View v) {
                tok.setBackgroundColor(Color.GREEN);

            }

            @Override
            protected void onDoubleClick(View v) {
                tok.setBackgroundColor(R.drawable.background_button6);


            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cs++;
                if(cs%2==1){
                    setAlarm(11,46);
                    s.setBackgroundColor(R.drawable.background_button_delete);
                    Toast.makeText(medicine.this, "alarm set", Toast.LENGTH_SHORT).show();
                }
                else{
                    cancelAlarm();
                    Toast.makeText(medicine.this, "alarm iptal", Toast.LENGTH_SHORT).show();
                    s.setBackgroundColor(R.drawable.background_button6);
                }
            }
        });






    }




    private void cancelAlarm() {
        Toast.makeText(medicine.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent2=new Intent(this,AlarmField.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent2,0);

        alarmManager.cancel(pendingIntent);
    }
    /*
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c=Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);
        Toast.makeText(medicine.this, "button clicked", Toast.LENGTH_SHORT).show();
        startAlarm();
        Toast.makeText(medicine.this, "button clicked222222", Toast.LENGTH_SHORT).show();
    }


    private void startAlarm() {
        Toast.makeText(medicine.this, "ALARM ON", Toast.LENGTH_SHORT).show();

        alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent=new Intent(this,AlertReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);
        //time = (c.getTimeInMillis() - (c.getTimeInMillis() % 60000));

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
        Intent alarm=new Intent(this,AlarmField.class);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
        startActivity(alarm);
    }

     */
    public void setAlarm( int hour,int minute){


        String myTime = String.valueOf(hour) + ":" + String.valueOf(minute);

        Date date = null;

        // today at your defined time Calendar
        Calendar customCalendar = new GregorianCalendar();
        // set hours and minutes
        customCalendar.set(Calendar.HOUR_OF_DAY, hour);
        customCalendar.set(Calendar.MINUTE, minute);
        customCalendar.set(Calendar.SECOND, 0);
        customCalendar.set(Calendar.MILLISECOND, 0);

        Date customDate = customCalendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {

            date = sdf.parse(myTime);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        if (date != null) {
            timeInMs = customDate.getTime();
        }


        Intent intent = new Intent(this, AlarmField.class);
        intent.putExtra("Key",ilac_adı.getText().toString());
        PendingIntent action = PendingIntent.getActivity(this, 0,intent,0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, timeInMs, action);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}