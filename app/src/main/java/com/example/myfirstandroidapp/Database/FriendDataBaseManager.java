package com.example.myfirstandroidapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myfirstandroidapp.Classes.Friend;

import java.util.ArrayList;

public class FriendDataBaseManager {

    public static final String DB_NAME = "FriendsRecords";
    public static final String DB_TABLE = "Freinds";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (FriendID INTEGER , FirstName TEXT, LastName TEXT, Gender TEXT, Age INTEGER , Address TEXT );";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public FriendDataBaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public FriendDataBaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow( int friendID , String fname, String lname , String gender , int age , String address) {
        synchronized(this.db) {

            ContentValues newProduct = new ContentValues();
            newProduct.put("FriendID", friendID);
            newProduct.put("FirstName", fname);
            newProduct.put("LastName", lname);
            newProduct.put("Gender", gender);
            newProduct.put("Age", age);
            newProduct.put("Address", address);

            try {
                db.insertOrThrow(DB_TABLE, null, newProduct);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public ArrayList<Friend> retrieveRows() {

        ArrayList<Friend> friendRow = new ArrayList<Friend>();
        String[] columns = new String[] {"FriendID" ,"FirstName", "LastName", "Gender" , "Age" , "Address"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            Friend s = new Friend(cursor.getInt(0) , cursor.getString(1), cursor.getString(2) , cursor.getString(3) , cursor.getInt(4) , cursor.getString(5));
            friendRow.add(s);
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return friendRow;
        //return studentRows;
    }

    public void clearRecords()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }

    public void deleteSingleRow(String id)
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE,"FriendID=?",new String[]{id+""});
        //db.close();
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
            System.out.println(" CREATED THE TABLE");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Products table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }

}
