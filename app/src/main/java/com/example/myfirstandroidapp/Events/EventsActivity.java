package com.example.myfirstandroidapp.Events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myfirstandroidapp.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
import android.widget.Button;
        import android.widget.ListView;
        import android.widget.Toast;

import com.example.myfirstandroidapp.Adapters.*;



public class EventsActivity extends AppCompatActivity {

    final Context context = this;

    ListView list;
    boolean[] checkBoxes;
    int boxIndex = 0;
    EventCustomAdapter adapter;
    Button addButton;

    public void onCreate(Bundle icicle) {
        setTheme(R.style.AppTheme);
        super.onCreate(icicle);
        setContentView(R.layout.activity_event);



        list = (ListView) findViewById(R.id.lvExp);
        String[] values = new String[]{
                "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2"};
        checkBoxes = new boolean[values.length];
        // default adapter
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.rowlayout, R.id.label, values);

        // use your custom layout
        adapter = new EventCustomAdapter(this, values);
        list = (ListView) findViewById(R.id.EventList);
        list.setAdapter(adapter);


        addButton = (Button) findViewById(R.id.newEvent);
        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(context, AddEventActivity.class);
                startActivity(intent);


            }
        });
    }
    public void Submit (View v) {
        boolean[] checkboxes = adapter.getCheckBoxState();

        String st = "You select ";
        for (int i = 0; i < list.getCount(); i++) {
            if (checkboxes[i] == true)
                st = st + adapter.getName(i) + " ";

        }
        Toast.makeText(getApplicationContext(), st + "out of " + list.getCount() + " items! ", Toast.LENGTH_LONG).show();
    }



}
