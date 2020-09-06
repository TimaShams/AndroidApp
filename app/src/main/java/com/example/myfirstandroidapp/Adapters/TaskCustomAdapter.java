

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

public class TaskCustomAdapter extends ArrayAdapter<Friend> {

    private final Context context;
    private final ArrayList<Friend> friendListValues;

    ViewHolder viewHolder;

    // Constructor
    public TaskCustomAdapter(Context context, ArrayList<Friend> friendListValues) {
        super(context, R.layout.rowlayout_todo, friendListValues);
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
            convertView = inflater.inflate(R.layout.rowlayout_todo, null);
            viewHolder = new ViewHolder();

            //cache the views
            viewHolder.labelText = (TextView) convertView.findViewById(R.id.tasknamelabel);

            //link the cached views to the convertview
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.labelText.setText("form");
        return convertView;
    }


    public int getID(int pos) {
        int val = friendListValues.get(pos).getId();
        return val;
    }


}


