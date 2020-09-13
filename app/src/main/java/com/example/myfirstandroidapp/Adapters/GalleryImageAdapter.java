package com.example.myfirstandroidapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myfirstandroidapp.R;

import com.example.myfirstandroidapp.Friends.profilePictureActivity;

public class GalleryImageAdapter extends BaseAdapter {

    private  Context mContext;
    private  String [] image;

    public GalleryImageAdapter(profilePictureActivity profilePictureActivity, String[] imageName) {
        mContext = profilePictureActivity;
        image = imageName;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 1
        final String imageName = image[i];

        // 2
        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(R.layout.profile_image_layout, null);
        }

        // 3
        final ImageView imageView = (ImageView)view.findViewById(R.id.dpImageView);

        // 4
        String mDrawableName = image[i];
        int resID = mContext.getResources().getIdentifier(mDrawableName , "drawable", mContext.getPackageName());
        imageView.setImageResource(resID);
        return view;

    }
}