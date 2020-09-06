package com.example.myfirstandroidapp.Todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

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

import com.example.myfirstandroidapp.FireMissilesDialogFragment;
import com.example.myfirstandroidapp.R;
import com.example.myfirstandroidapp.Adapters.*;
import com.example.myfirstandroidapp.Classes.*;
import com.example.myfirstandroidapp.Database.*;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class ToDoActivity extends  FragmentActivity implements FireMissilesDialogFragment.NoticeDialogListener {


        TaskCustomAdapter taskAdapter;

        private TaskDataBaseManager mydManager;
        private TextView response;
        private ListView taskListView;
        private EditText location, title;
        private Button addButton;
        private boolean recInserted;
        final Context context = this;
        private int taskId = 100;
        private ArrayList<Task> tableContent;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setTheme(R.style.AppTheme);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_todo); // includes content_main

            // DataBase


            mydManager = new TaskDataBaseManager(context);
            mydManager.openReadable();

            // Data
            tableContent = mydManager.retrieveRows();

            response = (TextView)findViewById(R.id.response);
            response.setText("Press MENU button to display menu");

            // View
            taskListView = (ListView)findViewById(R.id.taskRec);
            taskListView.setVisibility(View.VISIBLE);



            // Adapter
            taskAdapter = new TaskCustomAdapter(this, tableContent);
            taskListView.setAdapter(taskAdapter);


            // Add

            addButton = (Button) findViewById(R.id.add_button);
            addButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FireMissilesDialogFragment fmdl = new FireMissilesDialogFragment(ToDoActivity.this);
                    fmdl.show(fragmentManager,"dialog");
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

        public boolean insertRec() {

            response.setText("Enter information of the new product");
            taskListView.setVisibility(View.GONE);
            return true;
        }



        public boolean showRec() {

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

    @Override
    public void onDialogPositiveClick( DialogFragment dialog , String first, String second) {
        recInserted = mydManager.addRow(taskId++, first , second  , false);
        if (recInserted) {
            response.setText("The row in the student table is inserted");
        }
        else {
            response.setText("Sorry, errors when inserting to DB");
        }
        mydManager.openReadable();
        tableContent = mydManager.retrieveRows();

        // View
        taskListView = (ListView)findViewById(R.id.taskRec);
        taskListView.setVisibility(View.VISIBLE);
        taskAdapter = new TaskCustomAdapter(this, tableContent);
        taskListView.setAdapter(taskAdapter);

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}

