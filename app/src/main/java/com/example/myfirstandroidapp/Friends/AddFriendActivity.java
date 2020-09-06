package com.example.myfirstandroidapp.Friends;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstandroidapp.R;

public class AddFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_add); // includes content
    }


}
