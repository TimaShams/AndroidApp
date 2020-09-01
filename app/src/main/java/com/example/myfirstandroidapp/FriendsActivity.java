package com.example.myfirstandroidapp;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {

    CustomAdapter testadapter;


    private DatabaseManager mydManager;
    private TextView response;
    private ListView studentRec;
    private EditText stuId, fname, lname,yob,gender;
    private Button addButton;
    private Button updateButton;
    private TableLayout addLayout;
    private boolean recInserted;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        // DataBase


        mydManager = new DatabaseManager(context);
        response = (TextView)findViewById(R.id.response);
        studentRec = (ListView)findViewById(R.id.studentRec);
        addLayout = (TableLayout)findViewById(R.id.add_table);
        addLayout.setVisibility(View.GONE);
        response.setText("Press MENU button to display menu");

        addButton = (Button) findViewById(R.id.add_button);
        updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setVisibility(View.GONE);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stuId = (EditText)findViewById(R.id.stuId);
                fname = (EditText)findViewById(R.id.fname);
                lname = (EditText)findViewById(R.id.lanme);
                yob = (EditText)findViewById(R.id.yob);
                gender = (EditText)findViewById(R.id.gender);

                recInserted = mydManager.addRow(Integer.parseInt(stuId.getText().toString()), fname.getText().toString() , lname.getText().toString()  , Integer.parseInt(yob.getText().toString())  , gender.getText().toString() );
                addLayout.setVisibility(View.GONE);
                if (recInserted) {
                    response.setText("The row in the student table is inserted");
                }
                else {
                    response.setText("Sorry, errors when inserting to DB");
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(gender.getWindowToken(),    InputMethodManager.HIDE_NOT_ALWAYS);
                mydManager.close();
                stuId.setText("");
                fname.setText("");
                lname.setText("");
                yob.setText("");
                gender.setText("");

                studentRec.setAdapter(null);
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
        addLayout.setVisibility(View.VISIBLE);
        response.setText("Enter information of the new product");
        studentRec.setVisibility(View.GONE);
        return true;
    }

    public boolean showRec() {
        updateButton.setVisibility(View.VISIBLE);
        addLayout.setVisibility(View.GONE);
        studentRec.setVisibility(View.VISIBLE);
        mydManager.openReadable();
        ArrayList<Friend> tableContent = mydManager.retrieveRows();

        response.setText("The rows in the products table are: \n");
        // Back if wrong
        //ArrayAdapter<String> arrayAdpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tableContent);
        //studentRec.setAdapter(arrayAdpt);


        // test lines
        testadapter = new CustomAdapter(this, tableContent);
        studentRec.setAdapter(testadapter);
        return true;
    }

    public boolean removeRecs() {
        mydManager.clearRecords();
        response.setText("All Records are removed");
        studentRec.setAdapter(null);
        return true;
    }

    public void Submit (View v) {
        boolean[] checkboxes = testadapter.getCheckBoxState();

        String st = "You select ";
        for (int i = 0; i < studentRec.getCount(); i++) {
            if (checkboxes[i] == true)
                mydManager.deleteSingleRow(testadapter.getID(i));
            showRec();
            //st = st + testadapter.getID(i) + " ";// list.getAdapter().getItem(i).toString();

        }
        Toast.makeText(getApplicationContext(), st + "out of " + studentRec.getCount() + " items! ", Toast.LENGTH_LONG).show();
    }
}
