package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TABLE_TITLE + " TEXT," + DESCRIPTION + " TEXT," + STARTED + " TEXT," + FINISHED + " TEXT" + ");";

        db.execSQL(CREATE_TABLE_QUERY);
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
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        //close the DB connection
        sqLiteDatabase.close();
    }

    public int countTodos() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        //cursor type object is needed to capture the details return from the query
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor.getCount();
    }

    public List<TodoModel> getAllTodos() {
        //creating a generic list to store TodoModel type data to save
        List<TodoModel> todoModelList = new ArrayList<>();
        //sqlite object to read data
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()) { //check weather first element of the cursor is empty or not
            //if not empty (TRUE) loop until end of the object
            do{
                //todoModel to store retrieved data
                TodoModel todoModel = new TodoModel();

                todoModel.setId(cursor.getInt(0));
                todoModel.setTitle(cursor.getString(1));
                todoModel.setDescription(cursor.getString(2));
                todoModel.setStarted(cursor.getLong(3));
                todoModel.setFinished(cursor.getLong(4));

                //adding the retrieved data into the generic todoModelList
                todoModelList.add(todoModel);
            } while(cursor.moveToNext()); //loop while the last element
        }
        return todoModelList;
    }
}