package com.example.myfirstandroidapp.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfirstandroidapp.Database.FriendDataBaseManager;
import com.example.myfirstandroidapp.Classes.Friend;
import com.example.myfirstandroidapp.R;

import java.util.ArrayList;


public class FriendCustomAdapter extends ArrayAdapter<Friend> {
    private final Context context;
    private final ArrayList<Friend> friendListValues;

    ViewHolder viewHolder;

    // Constructor
    public FriendCustomAdapter(Context context, ArrayList<Friend> friendListValues, FriendDataBaseManager mydManager) {
        super(context, R.layout.rowlayout_friend, friendListValues);
        this.context = context;
        this.friendListValues = friendListValues;
    }


    // A single raw class
    private class ViewHolder {
        TextView nameLabel;
        TextView ageLabel;
        TextView genderLabel;
        TextView addressLabel;
        ImageButton editButton;
        ImageView displayPicture;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowlayout_friend, null);
            viewHolder = new ViewHolder();

            //cache the views
            viewHolder.nameLabel = (TextView) convertView.findViewById(R.id.friendNameLabel);
            viewHolder.ageLabel = (TextView) convertView.findViewById(R.id.friendAgeLabel);
            viewHolder.genderLabel = (TextView) convertView.findViewById(R.id.friendGenderLabel);
            viewHolder.addressLabel = (TextView) convertView.findViewById(R.id.friendAddressLabel);
            viewHolder.editButton = (ImageButton) convertView.findViewById(R.id.editFriendImageButton);
            viewHolder.displayPicture = (ImageView) convertView.findViewById(R.id.friendimageView);

            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.nameLabel.setText(friendListValues.get(position).getFname()+" "+friendListValues.get(position).getLname());
        viewHolder.ageLabel.setText(friendListValues.get(position).getAge()+" ");
        viewHolder.genderLabel.setText(friendListValues.get(position).getGender());
        viewHolder.addressLabel.setText(friendListValues.get(position).getAddress());
        //viewHolder.editButton.ste
        //viewHolder.displayPicture
        return convertView;
    }

    public String getID(int pos) {
        String val = friendListValues.get(pos).getFname();
        return val;
    }


}


