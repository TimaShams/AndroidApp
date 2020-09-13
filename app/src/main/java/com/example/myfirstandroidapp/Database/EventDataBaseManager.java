package com.example.myfirstandroidapp.Database;

import android.app.usage.UsageEvents;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myfirstandroidapp.Classes.Event;
import com.example.myfirstandroidapp.Classes.Friend;

import java.util.ArrayList;

public class EventDataBaseManager {

    public static final String DB_NAME = "EventsRecords";
    public static final String DB_TABLE = "EventTable";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (EventID INTEGER , Name TEXT, Location TEXT, DateTime TEXT );";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public EventDataBaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public EventDataBaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow( int EventID , String Name , String Location, String DateTime) {
        synchronized(this.db) {

            ContentValues newProduct = new ContentValues();
            newProduct.put("EventID", EventID);
            newProduct.put("Name", Name);
            newProduct.put("Location", Location);
            newProduct.put("DateTime", DateTime);

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

    public ArrayList<Event> retrieveRows() {

        ArrayList<Event> eventRow = new ArrayList<Event>();
        String[] columns = new String[] {"EventID" ,"Name", "Location", "DateTime" };
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            Event s = new Event(cursor.getInt(0) , cursor.getString(1), cursor.getString(2) , cursor.getString(3));
            eventRow.add(s);
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return eventRow;
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
        db.delete(DB_TABLE,"EventID=?",new String[]{id+""});
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
