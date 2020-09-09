

package com.example.myfirstandroidapp.Adapters;


        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.CheckBox;
        import android.widget.TextView;
        import com.example.myfirstandroidapp.Classes.Task;
        import com.example.myfirstandroidapp.R;

        import java.util.ArrayList;

public class TaskCustomAdapter extends ArrayAdapter<Task> {

    private final Context context;
    private final ArrayList<Task> taskArray;
    private ListOwner listOwner;
    private boolean[] checkBoxState;

    ViewHolder viewHolder;

    // Constructor
    public TaskCustomAdapter(Context context, ArrayList<Task> taskArray , DataTransferInterface dtInterface) {
        super(context, R.layout.rowlayout_todo, taskArray);
        this.context = context;
        this.taskArray = taskArray;
        this.dtInterface = dtInterface;
    }


    // A single raw class
    private class ViewHolder {
        TextView nameLabel;
        TextView locationLabel;
        TextView statusLabel;
        CheckBox statusCB;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowlayout_todo, null);
            viewHolder = new ViewHolder();

            //cache the views
            viewHolder.nameLabel = (TextView) convertView.findViewById(R.id.taskname);
            viewHolder.locationLabel = (TextView) convertView.findViewById(R.id.tasklocation);
            viewHolder.statusLabel = (TextView) convertView.findViewById(R.id.taskstatus);
            viewHolder.statusCB = (CheckBox) convertView.findViewById(R.id.taskstatuscheckbox);

            //link the cached views to the convertview
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.nameLabel.setText(taskArray.get(position).getName());
        viewHolder.locationLabel.setText(taskArray.get(position).getLocation());
        viewHolder.statusLabel.setText( Boolean.toString(taskArray.get(position).isStatus()) );
        viewHolder.statusCB.setChecked(taskArray.get(position).isStatus());

        viewHolder.statusCB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    dtInterface.onSetValues(taskArray.get(position).bringID() , true);         }
                else
                {
                    dtInterface.onSetValues(taskArray.get(position).bringID() , false);
                }

            }
        });

        return convertView;
    }

    public int getID(int pos) {
        int val = taskArray.get(pos).bringID();
        return val;
    }

    public boolean[] getCheckBoxState(){
        return checkBoxState;
    }

    public interface ListOwner {
        void push(int id);
    }


    public interface DataTransferInterface {
        public void onSetValues(int id , boolean status);
    }
    DataTransferInterface dtInterface;



}


