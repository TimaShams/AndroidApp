package com.example.myfirstandroidapp;


import android.content.Context;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
        import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<Friend> {
    private final Context context;
    private final ArrayList<Friend> friendListValues;
    private boolean checked = false;

    // boolean array for storing
    //the state of each CheckBox
    boolean[] checkBoxState;
    ViewHolder viewHolder;

    // Constructor
    public CustomAdapter(Context context, ArrayList<Friend> friendListValues, DatabaseManager mydManager) {
        super(context, R.layout.rowlayout, friendListValues);
        this.context = context;
        this.friendListValues = friendListValues;
        //checkBoxState = new boolean[friendListValues.size()];


    }


    // A single raw class
    private class ViewHolder
    {
        TextView labelText;
        CheckBox checkBox;
        Button edit;

        public CheckBox getCheckBox() {
            return checkBox;
        }
        public TextView getLabelText() {
            return labelText;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.rowlayout, null);
            viewHolder=new ViewHolder();

            //cache the views
            viewHolder.labelText =(TextView) convertView.findViewById(R.id.label);
            viewHolder.checkBox=(CheckBox) convertView.findViewById(R.id.checkBox);
            viewHolder.edit = (Button) convertView.findViewById(R.id.edit_button);

            //link the cached views to the convertview
            convertView.setTag( viewHolder);
        }
        else viewHolder= (ViewHolder) convertView.getTag();

        String form = String.format("%-17d  %-17s  %-17s " , friendListValues.get(position).getId(), friendListValues.get(position).getFname(), friendListValues.get(position).getLname() );
        viewHolder.labelText.setText(form);
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast toast = Toast.makeText(context, friendListValues.get(position).getFname() , Toast.LENGTH_SHORT);
                toast.show();

            }
        });


        //VITAL PART!!! Set the state of the
        //CheckBox using the boolean array
        //viewHolder.checkBox.setChecked(checkBoxState[position]);

        //for managing the state of the boolean
        //array according to the state of the
        //CheckBox
//        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                if(((CheckBox)v).isChecked()) {
//                    checkBoxState[position] = true;
//                }
//                else
//                    checkBoxState[position]=false;
//
//            }
//        });

        //return the view to be displayed
        return convertView;
    }

    // Check box state
    public boolean[] getCheckBoxState(){
        return checkBoxState;
    }

    //
    public int getID(int pos){
        int val = friendListValues.get(pos).getId();
        return val;
    }



}


