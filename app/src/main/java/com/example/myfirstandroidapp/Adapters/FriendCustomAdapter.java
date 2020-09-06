package com.example.myfirstandroidapp.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myfirstandroidapp.Database.DatabaseManager;
import com.example.myfirstandroidapp.Classes.Friend;
import com.example.myfirstandroidapp.R;

import java.util.ArrayList;


public class FriendCustomAdapter extends ArrayAdapter<Friend> {
    private final Context context;
    private final ArrayList<Friend> friendListValues;

    ViewHolder viewHolder;

    // Constructor
    public FriendCustomAdapter(Context context, ArrayList<Friend> friendListValues, DatabaseManager mydManager) {
        super(context, R.layout.rowlayout_friend, friendListValues);
        this.context = context;
        this.friendListValues = friendListValues;
    }


    // A single raw class
    private class ViewHolder {
        TextView labelText;

        public TextView getLabelText() {
            return labelText;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowlayout_friend, null);
            viewHolder = new ViewHolder();

            //cache the views
            viewHolder.labelText = (TextView) convertView.findViewById(R.id.label);

            //link the cached views to the convertview
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        String form = String.format("%-17d  %-17s  %-17s ", friendListValues.get(position).getId(), friendListValues.get(position).getFname(), friendListValues.get(position).getLname());
        viewHolder.labelText.setText(form);
        return convertView;
    }


    public int getID(int pos) {
        int val = friendListValues.get(pos).getId();
        return val;
    }


}


