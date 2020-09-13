package com.example.myfirstandroidapp.Events;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myfirstandroidapp.R;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDatePicker, btnTimePicker , eventSubmit;
    EditText name,location;
    TextView txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String userYear, userMonth, userDay, userHour, userMinute;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        btnDatePicker = (Button) findViewById(R.id.selectDateButton);
        btnTimePicker = (Button) findViewById(R.id.selectTimeButton);
        eventSubmit = (Button) findViewById(R.id.eventSubmit);
        txtDate = (TextView) findViewById(R.id.dateLabel);
        txtTime = (TextView) findViewById(R.id.timeLabel);
        name = (EditText) findViewById(R.id.eventNameLabel);
        location = (EditText) findViewById(R.id.eventLocationLabel);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        eventSubmit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {


        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {


                            if(dayOfMonth>9)
                                userDay = dayOfMonth+"";
                            else
                                userDay = "0"+dayOfMonth;


                            if(monthOfYear>8)
                                userMonth = (monthOfYear+1)+"";
                            else
                                userMonth = "0"+(monthOfYear+1);

                            userYear = year+"";

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            txtTime.setText(hourOfDay + ":" + minute);
                            if(hourOfDay<10)
                            userHour = hourOfDay+"";
                            else
                                userHour = "0"+hourOfDay;

                            if(minute<10)
                                userMinute = minute+"";
                            else
                                userMinute = "0"+minute;
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v == eventSubmit){

            String dateTime = userDay+"-"+userMonth+"-"+userYear+" "+userHour+":"+userMinute+":00";
            SimpleDateFormat formatter =new SimpleDateFormat("dd-MMM-yyyy HH:mm");
            System.out.println(dateTime);


            Intent i = new Intent(AddEventActivity.this, EventsActivity.class);
            i.putExtra("evenName",String.valueOf(name.getText()));
            i.putExtra("evenLocation",String.valueOf(location.getText()));
            i.putExtra("dateTime", dateTime );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }





    }// on create






}