package com.example.myfirstandroidapp.Friends;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.myfirstandroidapp.Classes.Friend;
import com.example.myfirstandroidapp.Events.AddEventActivity;
import com.example.myfirstandroidapp.Events.EventsActivity;
import com.example.myfirstandroidapp.R;
import com.example.myfirstandroidapp.Todo.TaskDialog;
import com.example.myfirstandroidapp.Todo.ToDoActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AddFriendActivity extends AppCompatActivity implements GalleryDialog.NoticeDialogListener {

    Button addFriend , adDP , deleteButton ;
    EditText fname, lname, address;
    RadioGroup gender;
    EditText age;
    Friend friend;
    ImageView viewImage;
    Button b;
    Context context = this;
    public static final String KEY_User_Document1 = "doc1";
    ImageView IDProf;
    Button Upload_Btn;
    private String Document_img1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_add); // includes content

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());



        IDProf=(ImageView)findViewById(R.id.profile);
        Upload_Btn=(Button)findViewById(R.id.addPicture);

        Upload_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AddFriendActivity.this , profilePictureActivity.class);
                startActivity(i);
                //selectImage(context);

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            int resID = context.getResources().getIdentifier(extras.getString("selectedImg") , "drawable", context.getPackageName());
            IDProf.setImageResource(resID);
            IDProf.setTag(extras.getString("selectedImg"));

        }else
        {IDProf.setImageResource(R.drawable.app_logo);

            IDProf.setTag("app_logo");


        }


        Intent i = getIntent();
        friend = (Friend) i.getSerializableExtra("FriendObject");

        fname = (EditText) findViewById(R.id.Editfname);
        lname = (EditText) findViewById(R.id.Editlname);
        address = (EditText) findViewById(R.id.Editlocation);
        gender = (RadioGroup) findViewById(R.id.Editgender);
        age = (EditText) findViewById(R.id.Editage);
        addFriend = (Button) findViewById(R.id.addFriend);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setVisibility(View.GONE);

        if(friend != null) {
            fname.setText(friend.getFname());
            lname.setText(friend.getLname());
            address.setText(friend.getAddress());
            if(friend.getGender().equals("Female"))
            {gender.check(R.id.femaleRadioButton);}
            else
            { gender.check(R.id.maleRadioButton);}
            System.out.println(friend.getAge());
            age.setText(friend.getAge()+"");
            addFriend.setText("Update");
            deleteButton.setVisibility(View.VISIBLE);
        }


        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //FriendsActivity.name = String.valueOf(fname.getText());
                Intent i = new Intent(AddFriendActivity.this, FriendsActivity.class);
                i.putExtra("image",String.valueOf(IDProf.getTag()));
                i.putExtra("fname",String.valueOf(fname.getText()));
                i.putExtra("lname",String.valueOf(lname.getText()));
                i.putExtra("address",String.valueOf(address.getText()));
                i.putExtra("gender",((RadioButton)findViewById(gender.getCheckedRadioButtonId()))
                        .getText().toString());
                i.putExtra("age",Integer.parseInt(String.valueOf(age.getText())));
                if(friend != null)
                { i.putExtra("updateFriend", friend.getFriendID());
                    System.out.println(friend.getFriendID());
                    System.out.println(i.getIntExtra("updateFriend" , -1));
                }
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //FriendsActivity.name = String.valueOf(fname.getText());
                Intent i = new Intent(AddFriendActivity.this, FriendsActivity.class);
                i.putExtra("deleteFriend", friend.getFriendID());
                    System.out.println(friend.getFriendID());
                    System.out.println(i.getIntExtra("updateFriend" , -1));
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

            }
        });




    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String first, String second) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds options to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        IDProf.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                IDProf.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }






}
