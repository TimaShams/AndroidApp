package com.example.myfirstandroidapp.Todo;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstandroidapp.R;
import com.example.myfirstandroidapp.Adapters.*;
import com.example.myfirstandroidapp.Classes.*;
import com.example.myfirstandroidapp.Database.*;
import com.hudomju.swipe.SwipeToDismissTouchListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class ToDoActivity extends  FragmentActivity implements TaskDialog.NoticeDialogListener , ExpandableListAdapter.DataTransferInterface ,  TaskCustomAdapter.DataTransferInterface , AdapterView.OnItemSelectedListener {


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
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ArrayList<Task> completedList;
    ArrayList<Task> unCompletedList;

    @Override
        protected void onCreate(Bundle savedInstanceState) {





            setTheme(R.style.AppTheme);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_todo); // includes content_main

            // DataBase

            mydManager = new TaskDataBaseManager(context);
            mydManager.openReadable();


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);



        // preparing list data
        prepareDBdata();

            // Data
            //tableContent = mydManager.retrieveRows();

            response = (TextView)findViewById(R.id.response);
            response.setText("Press MENU button to display menu");

            // View
            taskListView = (ListView)findViewById(R.id.taskRec);
            taskListView.setVisibility(View.VISIBLE);


            // Adapter



            // Add

            addButton = (Button) findViewById(R.id.add_button);
            addButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    TaskDialog fmdl = new TaskDialog(ToDoActivity.this);
                    fmdl.show(fragmentManager,"dialog");
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
            taskAdapter = new TaskCustomAdapter(this, tableContent , this);
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

        SharedPreferences TaskID = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = TaskID.edit();
        System.out.println(TaskID.contains("ID"));
        if(!TaskID.contains("ID"))
        {
            editor.putInt("ID" , 1000);
        }
        else
        {
            editor.putInt("ID" , TaskID.getInt("ID", -1)+1);
        }

        editor.commit();

        recInserted = mydManager.addRow(TaskID.getInt("ID", -1), first , second  , false);

        if (recInserted) {
            response.setText("The row in the student table is inserted");
        }
        else {
            response.setText("Sorry, errors when inserting to DB");
        }

        prepareDBdata();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onSetValues(int id , boolean status) {

        mydManager.editTaskStatus(id,status);
        prepareDBdata();
        System.out.println(id+" "+status);



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        prepareDBdata();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        prepareDBdata();

    }


    ArrayList<String> taskHeader;
    HashMap<String, ArrayList<Task>> taskHeaderChild;

    private void prepareDBdata() {


        taskHeader = new ArrayList<String>();
        taskHeaderChild = new HashMap<String, ArrayList<Task>>();
        taskHeader.add("UnCompleted");
        taskHeader.add("Completed");

        completedList = new ArrayList<Task>();
        unCompletedList = new ArrayList<Task>();
        ArrayList<Task> fullList =  mydManager.retrieveRows();

        for ( int j = 0 ; j < fullList.size() ; j++)
            if(fullList.get(j).isStatus())
                completedList.add(fullList.get(j));


        for ( int j = 0 ; j < fullList.size() ; j++)
            if(!fullList.get(j).isStatus())
                unCompletedList.add(fullList.get(j));


        taskHeaderChild.put(taskHeader.get(0), unCompletedList); // Header, Child data
        taskHeaderChild.put(taskHeader.get(1), completedList);
        listAdapter = new ExpandableListAdapter(this , taskHeader , taskHeaderChild , this );

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.expandGroup(0);
        expListView.expandGroup(1);
        listAdapter.notifyDataSetChanged();
    }



}

