package com.example.myfirstandroidapp.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myfirstandroidapp.Adapters.MapsActivity;
import com.example.myfirstandroidapp.Events.EventsActivity;
import com.example.myfirstandroidapp.Friends.FriendsActivity;
import com.example.myfirstandroidapp.R;
import com.example.myfirstandroidapp.Todo.ToDoActivity;

public class MainActivity extends AppCompatActivity {

    Button button1 , button2 , button3 , button4 ;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, FriendsActivity.class);
                startActivity(intent);
            }
        });



        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, ToDoActivity.class);
                startActivity(intent);

            }
        });



        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, EventsActivity.class);
                startActivity(intent);
            }
        });


        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                Intent intent = new Intent(context, EventsActivity.class);
                startActivity(intent);




            }
        });


    }
}