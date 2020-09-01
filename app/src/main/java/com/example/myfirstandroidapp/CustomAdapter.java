package com.example.myfirstandroidapp;


import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.CheckBox;
        import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<Friend> {
    private final Context context;
    private final ArrayList<Friend> values;
    private boolean checked = false;

    // boolean array for storing
    //the state of each CheckBox
    boolean[] checkBoxState;

    ViewHolder viewHolder;

    // Constructor
    public CustomAdapter(Context context, ArrayList<Friend> values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
        checkBoxState = new boolean[values.size()];
    }

    // A single raw class
    private class ViewHolder
    {
        TextView labelText;
        CheckBox checkBox;

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

            //link the cached views to the convertview
            convertView.setTag( viewHolder);


        }
        else
            viewHolder=(ViewHolder) convertView.getTag();

        String form = String.format("%-17d  %-17s  %-17s " , values.get(position).getId(),values.get(position).getFname(),values.get(position).getLname() );
        viewHolder.labelText.setText(form);

        //VITAL PART!!! Set the state of the
        //CheckBox using the boolean array
        viewHolder.checkBox.setChecked(checkBoxState[position]);

        //for managing the state of the boolean
        //array according to the state of the
        //CheckBox
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    checkBoxState[position] = true;
                }
                else
                    checkBoxState[position]=false;

            }
        });

        //return the view to be displayed
        return convertView;
    }

    // Check box state
    public boolean[] getCheckBoxState(){
        return checkBoxState;
    }

    //
    public int getID(int pos){
        int val = values.get(pos).getId();
        return val;
    }


}


