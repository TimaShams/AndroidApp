package com.example.myfirstandroidapp.Friends;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstandroidapp.Adapters.ExpandableListAdapter;
import com.example.myfirstandroidapp.Adapters.FriendCustomAdapter;
import com.example.myfirstandroidapp.Classes.Friend;
import com.example.myfirstandroidapp.Classes.Task;
import com.example.myfirstandroidapp.Database.FriendDataBaseManager;
import com.example.myfirstandroidapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class FriendsActivity extends AppCompatActivity {

    FriendCustomAdapter friendAdapter;

    public static String name = "" ;

    private FriendDataBaseManager mydManager;
    private TextView response;
    private ListView studentRecordListView;
    private EditText address, fname, lname, age ,gender;
    private Button addButton;
    private Button updateButton;
    private TableLayout addTableLayout;
    private boolean recInserted;
    final Context context = this;
    ArrayList<Friend> tableContent;

    public FriendsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend); // includes content_main

        // DataBase

        mydManager = new FriendDataBaseManager(context);
        mydManager.openReadable();
        // Data
        tableContent = mydManager.retrieveRows();

        response = (TextView)findViewById(R.id.response);
        response.setText("Press MENU button to display menu");

        // View
        studentRecordListView = (ListView)findViewById(R.id.studentRec);
        studentRecordListView.setVisibility(View.VISIBLE);


        // Update
        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setVisibility(View.GONE);

        // Adapter
        friendAdapter = new FriendCustomAdapter(this, tableContent , mydManager);
        studentRecordListView.setAdapter(friendAdapter);


        // Add
        addTableLayout = (TableLayout)findViewById(R.id.add_table);
        addTableLayout.setVisibility(View.GONE);
        addButton = (Button) findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, AddFriendActivity.class);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            SharedPreferences FriendID = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = FriendID.edit();
            System.out.println(FriendID.contains("friendID"));
            if(!FriendID.contains("friendID"))
            {
                editor.putInt("friendID" , 1000);
            }
            else
            {
                editor.putInt("friendID" , FriendID.getInt("friendID", -1)+1);
            }

            editor.commit();

            if(extras.containsKey("updateFriend"))
            {
                System.out.println("updateFriend Value after"+extras.getInt("updateFriend"));

                mydManager.deleteSingleRow(extras.getInt("updateFriend")+"");
                recInserted = mydManager.addRow(
                        extras.getInt("updateFriend"),
                        extras.getString("fname"),
                        extras.getString("lname"),
                        extras.getString("gender"),
                        extras.getInt("age" , 0),
                        extras.getString("address"),
                        extras.getString("image")
                );

            }
            else if (extras.containsKey("deleteFriend"))
            {
                mydManager.deleteSingleRow(extras.getInt("deleteFriend")+"");

            }else
            {
                recInserted = mydManager.addRow(
                        FriendID.getInt("friendID", -1),
                        extras.getString("fname"),
                        extras.getString("lname"),
                        extras.getString("gender"),
                        extras.getInt("age" , 0),
                        extras.getString("address"),extras.getString("image")
                );

            }



            if (recInserted) {
                System.out.println("The row in the student table is inserted");
            }
            else {
                System.out.println("Sorry, errors when inserting to DB");
            }

            prepareDBdata();

        }





        studentRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend= tableContent.get(position);
                Snackbar.make(view, friend.getLname() , Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                Intent intent = new Intent(context, AddFriendActivity.class);
                intent.putExtra("FriendObject" , friend);
                startActivity(intent);

            }
        });

        studentRecordListView.setAdapter(friendAdapter);
        friendAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.insert_rows:
                insertRec();
                break;
            case R.id.list_rows:
                showRec();
                break;
            case R.id.remove_rows:
                removeRecs();
                break;
        }
        //return true;
        return super.onOptionsItemSelected(item);
    }


    public boolean insertRec() {


        updateButton.setVisibility(View.GONE);
        addTableLayout.setVisibility(View.VISIBLE);
        response.setText("Enter information of the new product");
        studentRecordListView.setVisibility(View.GONE);


        return true;
    }



    public boolean showRec() {

        updateButton.setVisibility(View.VISIBLE);
        addTableLayout.setVisibility(View.GONE);
        studentRecordListView.setVisibility(View.VISIBLE);
        mydManager.openReadable();
        ArrayList<Friend> tableContent = mydManager.retrieveRows();

        response.setText("The rows in the products table are: \n");

        // test lines
        friendAdapter = new FriendCustomAdapter(this, tableContent, mydManager);
        studentRecordListView.setAdapter(friendAdapter);
        return true;
    }

    public boolean removeRecs() {
        mydManager.clearRecords();
        response.setText("All Records are removed");
        studentRecordListView.setAdapter(null);
        return true;
    }

    public boolean editRecs() {
        mydManager.clearRecords();
        response.setText("All Records are removed");
        studentRecordListView.setAdapter(null);
        return true;
    }

    public void Submit (View v) {

        String st = "You select ";
        for (int i = 0; i < studentRecordListView.getCount(); i++) {
            if (true)
                mydManager.deleteSingleRow(friendAdapter.getID(i));
            showRec();
        }
        Toast.makeText(getApplicationContext(), st + "out of " + studentRecordListView.getCount() + " items! ", Toast.LENGTH_LONG).show();
    }


    private void prepareDBdata() {

        tableContent =  mydManager.retrieveRows();
        friendAdapter = new FriendCustomAdapter(this, tableContent , mydManager);
        // setting list adapter
        studentRecordListView.setAdapter(friendAdapter);
        friendAdapter.notifyDataSetChanged();
    }


}
