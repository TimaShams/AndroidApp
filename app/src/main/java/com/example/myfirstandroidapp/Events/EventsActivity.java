package com.example.myfirstandroidapp.Events;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstandroidapp.Classes.Event;
import com.example.myfirstandroidapp.Classes.Friend;
import com.example.myfirstandroidapp.Classes.Utility;
import com.example.myfirstandroidapp.Database.EventDataBaseManager;
import com.example.myfirstandroidapp.Friends.AddFriendActivity;
import com.example.myfirstandroidapp.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
import android.widget.Button;
        import android.widget.ListView;
import android.widget.Toast;

import com.example.myfirstandroidapp.Adapters.*;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class EventsActivity extends AppCompatActivity {


    final Context context = this;
    public static int [] check;
    ListView eventList;
    boolean[] checkBoxes;
    int boxIndex = 0;
    Button addButton;
    EventDataBaseManager mydManager;
    ArrayList<Event> tableContent;
    EventCustomAdapter eventAdapter;
    boolean recInserted;
    public void onCreate(Bundle icicle) {
        setTheme(R.style.AppTheme);
        super.onCreate(icicle);
        setContentView(R.layout.activity_event);


        mydManager = new EventDataBaseManager(context);
        mydManager.openReadable();
        tableContent = mydManager.retrieveRows();
        eventList = (ListView) findViewById(R.id.EventList);
        checkBoxes = new boolean[tableContent.size()];
        // default adapter
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.rowlayout, R.id.label, values);

        // use your custom layout
        eventAdapter = new EventCustomAdapter(this, tableContent);
        eventList = (ListView) findViewById(R.id.EventList);
        eventList.setAdapter(eventAdapter);


        addButton = (Button) findViewById(R.id.eventAddButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(context, AddEventActivity.class);
                startActivity(intent);


            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            SharedPreferences EventID = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = EventID.edit();
            System.out.println(EventID.contains("eventID"));
            if (!EventID.contains("eventID")) {
                editor.putInt("eventID", 1000);
            } else {
                editor.putInt("eventID", EventID.getInt("eventID", -1) + 1);
            }

            editor.commit();

            boolean recInserted = mydManager.addRow(
                    EventID.getInt("eventID", -1),
                    extras.getString("evenName"),
                    extras.getString("evenLocation"),
                    extras.getString("dateTime")
            );

            prepareDBdata();

        }

    }// create
    public void Submit (View v) {

        Utility.getInstance().getList();
        boolean[] checkboxes = Utility.getInstance().getList();
        String st = "You select ";
        for (int i = 0; i < eventList.getCount(); i++) {
            if (checkboxes[i] == true)
            {mydManager.deleteSingleRow(eventAdapter.getItem(i).getEventID()+"");
                }
        }

        System.out.println(st);
        Toast.makeText(getApplicationContext(), st + "out of " + eventList.getCount() + " items! ", Toast.LENGTH_LONG).show();
        prepareDBdata();

    }

    private void prepareDBdata() {

        tableContent =  mydManager.retrieveRows();
        eventAdapter = new EventCustomAdapter(this, tableContent);
        eventList.setAdapter(eventAdapter);
        eventAdapter.notifyDataSetChanged();
    }


}
