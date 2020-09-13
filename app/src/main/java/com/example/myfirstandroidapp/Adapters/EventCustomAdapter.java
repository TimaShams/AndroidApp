package com.example.myfirstandroidapp.Adapters;
import com.example.myfirstandroidapp.Classes.Event;
import com.example.myfirstandroidapp.Classes.Utility;
import com.example.myfirstandroidapp.R;
        import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
        import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventCustomAdapter extends ArrayAdapter<Event> {
        private final Context context;
        private boolean checked = false;
        private ArrayList<Event> values;

        // boolean array for storing
        //the state of each CheckBox
        boolean[] checkBoxState;


        ViewHolder viewHolder;


        // Constructor
        public EventCustomAdapter(Context context, ArrayList <Event> values) {
            super(context, R.layout.rowlayout_event, values);
            this.context = context;
            this.values = values;
            checkBoxState = new boolean[values.size()];
            Utility.getInstance().setList(checkBoxState);
        }

    public String getName(int i) {
            return values.get(i).getEventName();
    }

    // A single raw class
        private class ViewHolder
        {
            ImageView flag;
            TextView name;
            CheckBox checkBox;
            TextView location;
            TextView date;
            TextView time;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.rowlayout_event, null);
                viewHolder = new EventCustomAdapter.ViewHolder();

                //cache the views
                viewHolder.name = (TextView) convertView.findViewById(R.id.eventNameLabel);
                viewHolder.location = (TextView) convertView.findViewById(R.id.eventLocationLabel);
                viewHolder.date = (TextView) convertView.findViewById(R.id.eventDateLabel);
                viewHolder.time = (TextView) convertView.findViewById(R.id.eventTimeLabel);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.eventCheckBox);
                viewHolder.flag = (ImageView) convertView.findViewById(R.id.eventFlag);



                convertView.setTag(viewHolder);
            } else viewHolder = (ViewHolder) convertView.getTag();

            String dateTime = values.get(position).getEventDateTime();
            SimpleDateFormat formatter =new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = null;
            try {
                date = formatter.parse(dateTime);
                System.out.println("selected "+date);
                if (new Date().after(date)) {
                    System.out.println(" your date is before the current date ");
                    viewHolder.flag.setImageResource(R.drawable.history);
                }
                else{
                    viewHolder.flag.setImageResource(R.drawable.current);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }




            viewHolder.name.setText(values.get(position).getEventName());
            viewHolder.location.setText(values.get(position).getEventLocation());
            viewHolder.date.setText(values.get(position).getEventDateTime());
            viewHolder.time.setText(values.get(position).getEventDateTime());
            viewHolder.checkBox.setChecked(checkBoxState[position]);




            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if(((CheckBox)v).isChecked()) {
                        checkBoxState[position] = true;
                    }
                    else
                        checkBoxState[position]=false;

                    Utility.getInstance().setList(checkBoxState);

                }
            });
            //return the view to be displayed
            return convertView;
        }//get view

        // Check box state
        public boolean[] getCheckBoxState(){
            return checkBoxState;
        }


    }


