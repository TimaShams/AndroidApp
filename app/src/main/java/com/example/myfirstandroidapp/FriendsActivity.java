package com.example.myfirstandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {

    CustomAdapter friendAdapter;

    //first
    //name, last name, gender, age and address

    private DatabaseManager mydManager;
    private TextView response;
    private ListView studentRecordListView;
    private EditText address, fname, lname, age ,gender;
    private Button addButton;
    private Button updateButton;
    private TableLayout addTableLayout;
    private boolean recInserted;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_main); // includes content_main

        // DataBase

        mydManager = new DatabaseManager(context);
        response = (TextView)findViewById(R.id.response);

        studentRecordListView = (ListView)findViewById(R.id.studentRec);
        addTableLayout = (TableLayout)findViewById(R.id.add_table);
        addTableLayout.setVisibility(View.GONE);
        response.setText("Press MENU button to display menu");

        addButton = (Button) findViewById(R.id.add_button);
        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setVisibility(View.GONE);



        //
        updateButton.setVisibility(View.VISIBLE);
        addTableLayout.setVisibility(View.GONE);
        studentRecordListView.setVisibility(View.VISIBLE);
        mydManager.openReadable();

        final ArrayList<Friend> tableContent = mydManager.retrieveRows();

        response.setText("The rows in the products table are: \n");
        friendAdapter = new CustomAdapter(this, tableContent);
        studentRecordListView.setAdapter(friendAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                address = (EditText)findViewById(R.id.stuId);
                fname = (EditText)findViewById(R.id.fname);
                lname = (EditText)findViewById(R.id.lanme);
                age = (EditText)findViewById(R.id.yob);
                gender = (EditText)findViewById(R.id.gender);

                recInserted = mydManager.addRow(Integer.parseInt(address.getText().toString()), fname.getText().toString() , lname.getText().toString()  , Integer.parseInt(age.getText().toString())  , gender.getText().toString() );
                addTableLayout.setVisibility(View.GONE);
                if (recInserted) {
                    response.setText("The row in the student table is inserted");
                }
                else {
                    response.setText("Sorry, errors when inserting to DB");
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(gender.getWindowToken(),    InputMethodManager.HIDE_NOT_ALWAYS);
                mydManager.close();
                address.setText("");
                fname.setText("");
                lname.setText("");
                age.setText("");
                gender.setText("");

                studentRecordListView.setAdapter(null);
            }
        });


        studentRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Friend friend= tableContent.get(position);

                Snackbar.make(view, friend.getFname() , Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                System.out.println("BOoooooooooooooo");
            }
        });


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
//        Intent intent = new Intent(context, AddFriendActivity.class);
//        startActivity(intent);

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
        friendAdapter = new CustomAdapter(this, tableContent);
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
        boolean[] checkboxes = friendAdapter.getCheckBoxState();

        String st = "You select ";
        for (int i = 0; i < studentRecordListView.getCount(); i++) {
            if (checkboxes[i] == true)
                mydManager.deleteSingleRow(friendAdapter.getID(i));
            showRec();
            //st = st + testadapter.getID(i) + " ";// list.getAdapter().getItem(i).toString();

        }
        Toast.makeText(getApplicationContext(), st + "out of " + studentRecordListView.getCount() + " items! ", Toast.LENGTH_LONG).show();
    }
}
