package com.example.myfirstandroidapp.Todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.example.myfirstandroidapp.R;
import com.example.myfirstandroidapp.Adapters.*;
import com.example.myfirstandroidapp.Classes.*;
import com.example.myfirstandroidapp.Database.*;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class ToDoActivity extends AppCompatActivity {

        TaskCustomAdapter taskAdapter;

        private TaskDataBaseManager mydManager;
        private TextView response;
        private ListView taskListView;
        private EditText location, title;
        private Button addButton;
        private Button updateButton;
        private TableLayout addTableLayout;
        private boolean recInserted;
        final Context context = this;
        private int taskId = 100;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setTheme(R.style.AppTheme);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_todo); // includes content_main

            // DataBase

            mydManager = new TaskDataBaseManager(context);
            mydManager.openReadable();

            // Data
            final ArrayList<Task> tableContent = mydManager.retrieveRows();

            response = (TextView)findViewById(R.id.response);
            response.setText("Press MENU button to display menu");

            // View
            taskListView = (ListView)findViewById(R.id.taskRec);
            taskListView.setVisibility(View.VISIBLE);


            // Update
            updateButton = (Button) findViewById(R.id.updateButton);
            updateButton.setVisibility(View.GONE);

            // Adapter
            taskAdapter = new TaskCustomAdapter(this, tableContent);
            taskListView.setAdapter(taskAdapter);


            // Add
            addTableLayout = (TableLayout)findViewById(R.id.add_table);
            addTableLayout.setVisibility(View.GONE);
            addButton = (Button) findViewById(R.id.add_button);
            addButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    title = (EditText)findViewById(R.id.taskname);
                    location = (EditText)findViewById(R.id.tasklocation);
                    recInserted = mydManager.addRow(taskId++, title.getText().toString() , location.getText().toString()  , false);
                    addTableLayout.setVisibility(View.GONE);
                    if (recInserted) {
                        response.setText("The row in the student table is inserted");
                    }
                    else {
                        response.setText("Sorry, errors when inserting to DB");
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(location.getWindowToken(),    InputMethodManager.HIDE_NOT_ALWAYS);
                    mydManager.close();
                    location.setText("");
                    title.setText("");
                    taskListView.setAdapter(null);
                }
            });


            taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Task task= tableContent.get(position);

                    Snackbar.make(view, task.getId() , Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();

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
            taskListView.setVisibility(View.GONE);


            return true;
        }



        public boolean showRec() {

            updateButton.setVisibility(View.VISIBLE);
            addTableLayout.setVisibility(View.GONE);
            taskListView.setVisibility(View.VISIBLE);
            mydManager.openReadable();
            ArrayList<Task> tableContent = mydManager.retrieveRows();

            response.setText("The rows in the products table are: \n");

            // test lines
            taskAdapter = new TaskCustomAdapter(this, tableContent);
            taskListView.setAdapter(taskAdapter);
            return true;
        }

        public boolean removeRecs() {
            mydManager.clearRecords();
            response.setText("All Records are removed");
            taskListView.setAdapter(null);
            return true;
        }

        public boolean editRecs() {
            mydManager.clearRecords();
            response.setText("All Records are removed");
            taskListView.setAdapter(null);
            return true;
        }

        public void Submit (View v) {

            String st = "You select ";
            for (int i = 0; i < taskListView.getCount(); i++) {
                if (true)
                    mydManager.deleteSingleRow(taskAdapter.getID(i));
                showRec();
            }
            Toast.makeText(getApplicationContext(), st + "out of " + taskListView.getCount() + " items! ", Toast.LENGTH_LONG).show();
        }
    }

