package com.example.myfirstandroidapp.Friends;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.example.myfirstandroidapp.Adapters.GalleryImageAdapter;
import com.example.myfirstandroidapp.Events.AddEventActivity;
import com.example.myfirstandroidapp.Events.EventsActivity;
import com.example.myfirstandroidapp.R;

import java.io.Serializable;

public class profilePictureActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_dialog); // includes content_main

        final String [] imageName = {"f1" , "f2" , "f3" , "f4" , "f5"};
        GridView gv = (GridView) findViewById(R.id.gridview);
        final GalleryImageAdapter galleryAdapter = new GalleryImageAdapter(this, imageName);
        gv.setAdapter(galleryAdapter);


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String img = imageName[i];
            System.out.println(img);
                Intent intent = new Intent(profilePictureActivity.this, AddFriendActivity.class);
                intent.putExtra("selectedImg",imageName[i]);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });


    }




}
