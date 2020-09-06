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

    public static final String DB_NAME = "StudentRecords";
    public static final String DB_TABLE = "StudentInfo";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (StudentID INTEGER, FirstName TEXT, LastName TEXT, YearOfBirth INTEGER , Gender TEXT );";
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

    public boolean addRow(Integer id, String fname, String lname , Integer yob , String gender) {
        synchronized(this.db) {

            ContentValues newProduct = new ContentValues();
            newProduct.put("StudentID", id);
            newProduct.put("FirstName", fname);
            newProduct.put("LastName", lname);
            newProduct.put("YearOfBirth", yob);
            newProduct.put("Gender", gender);

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

        ArrayList<Friend> substudentRows = new ArrayList<Friend>();

        String[] columns = new String[] {"StudentID", "FirstName", "LastName" , "YearOfBirth" , "Gender"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            Friend s = new Friend(cursor.getInt(0), cursor.getString(1) , cursor.getString(2) , cursor.getInt(3) , cursor.getString(4));
            substudentRows.add(s);
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return substudentRows;
        //return studentRows;
    }

    public void clearRecords()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }

    public void deleteSingleRow(int id)
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE,"StudentID=?",new String[]{id+""});
        db.close();
    }



    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Products table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }

}
