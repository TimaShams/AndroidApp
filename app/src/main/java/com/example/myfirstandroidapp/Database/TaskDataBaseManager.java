
package com.example.myfirstandroidapp.Database;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import com.example.myfirstandroidapp.Classes.*;

        import java.util.ArrayList;

public class TaskDataBaseManager {

    public static final String DB_NAME = "TaskRecords";
    public static final String DB_TABLE = "TaskInfo";

    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (TaskID INTEGER, Title TEXT, Location TEXT, Status Boolean );";

    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public TaskDataBaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public TaskDataBaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow(Integer id, String title, String location , Boolean status) {
        synchronized(this.db) {

            ContentValues newProduct = new ContentValues();
            newProduct.put("TaskID", id);
            newProduct.put("Title", title);
            newProduct.put("Location", location);
            newProduct.put("Status", status);


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


    public ArrayList<Task> retrieveRows() {

        ArrayList<Task> taskRows = new ArrayList<Task>();

        String[] columns = new String[] {"TaskID", "Title", "Location" , "Status" };
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            Task s = new Task(cursor.getInt(0), cursor.getString(1) , cursor.getString(2) , cursor.getInt(3) > 0);
            taskRows.add(s);
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return taskRows;
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
        db.delete(DB_TABLE,"TaskID=?",new String[]{id+""});
        db.close();
    }

    public void editTaskStatus(int id , Boolean status )
    {
        ContentValues cv = new ContentValues();
        cv.put("Status",status);
        db.update("TaskInfo", cv, "TaskID = ?", new String[]{id+""});
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
