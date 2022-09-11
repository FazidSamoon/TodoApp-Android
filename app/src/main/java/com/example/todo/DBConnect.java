package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBConnect extends SQLiteOpenHelper {
    private static final int VERSION_NUMBER = 1;
    private static final String DB_NAME = "todo_app";
    private static final String TABLE_NAME = "todo";
    private static final String TABLE_TITLE = "title";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";
    private static final String ID = "id";
    private static final String DESCRIPTION = "description";

    public DBConnect(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION_NUMBER);
        System.out.println("db object created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TABLE_TITLE + " TEXT," + DESCRIPTION + " TEXT," + STARTED + " TEXT," + FINISHED + " TEXT" + ");";

        db.execSQL(CREATE_TABLE_QUERY);
        System.out.println("table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //in case of db parameter change should drop the table first since columns have changed
        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE_QUERY);
        //then call the onCreate() function to create a new table
        onCreate(db);
    }

    public void addTodos(TodoModel todoModel) {
        System.out.println("hell");
        //use to write data into database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //to structure data prior to the saving
        //store as key value pairs
        //similar to bundle in shared preferences
        ContentValues contentValues = new ContentValues();

        contentValues.put(TABLE_TITLE, todoModel.getTitle());
        contentValues.put(DESCRIPTION, todoModel.getDescription());
        contentValues.put(STARTED, todoModel.getStarted());
        contentValues.put(FINISHED, todoModel.getFinished());

        //save data into table
        long i = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        System.out.println(i);

        //close the DB co
        sqLiteDatabase.close();
    }
}