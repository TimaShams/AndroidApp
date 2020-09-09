package com.example.myfirstandroidapp.Adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.myfirstandroidapp.Classes.Task;
import com.example.myfirstandroidapp.R;
import com.example.myfirstandroidapp.Todo.ToDoActivity;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    ViewHolder viewHolder;

    private Context _context;
//    private List<String> _listDataHeader; // header titles
//    private HashMap<String, List<String>> _listDataChild;

    private  ArrayList<String> _taskHeader;
    private  HashMap<String, ArrayList<Task>> _taskHeaderChild;

    public ExpandableListAdapter(Context context, ArrayList<String> taskHeader, HashMap<String, ArrayList<Task>> taskHeaderChild , DataTransferInterface dtInterface )  {

        this._context = context;
//        this._listDataHeader = listDataHeader;
//        this._listDataChild = listChildData;
        this._taskHeader = taskHeader;
        this._taskHeaderChild = taskHeaderChild;
        this.dtInterface = dtInterface;
    }

    @Override
    public Task getChild(int groupPosition, int childPosititon) {
        return this._taskHeaderChild.get(this._taskHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // A single raw class
    private class ViewHolder {
        TextView nameLabel;
        TextView locationLabel;
        TextView statusLabel;
        CheckBox statusCB;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Task child = (Task) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.rowlayout_todo, null);

            // New part
            viewHolder = new ViewHolder();
            viewHolder.nameLabel = (TextView) convertView.findViewById(R.id.taskname);
            viewHolder.locationLabel = (TextView) convertView.findViewById(R.id.tasklocation);
            viewHolder.statusLabel = (TextView) convertView.findViewById(R.id.taskstatus);
            viewHolder.statusCB = (CheckBox) convertView.findViewById(R.id.taskstatuscheckbox);
            //link the cached views to the convertview
            convertView.setTag(viewHolder);
        }  else viewHolder = ( ViewHolder) convertView.getTag();


        viewHolder.nameLabel.setText(child.getName());
        viewHolder.locationLabel.setText(child.getLocation());
        viewHolder.statusLabel.setText( Boolean.toString(child.isStatus()) );
        viewHolder.statusCB.setChecked(child.isStatus());

        viewHolder.statusCB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    dtInterface.onSetValues(child.bringID() , true);         }
                else
                {
                    dtInterface.onSetValues(child.bringID() , false);
                }

            }
        });

        return convertView;

    }

    DataTransferInterface dtInterface;

    public interface DataTransferInterface {
        public void onSetValues(int id , boolean status);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._taskHeaderChild.get(this._taskHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._taskHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._taskHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
