package com.example.todo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private ListView listView;
    private TextView textView;
    private DBConnect dbConnect;
    private List<TodoModel> todoModelList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        //making references for the elements in the view
        button = findViewById(R.id.button);
        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.textView3);

        //db connection to interact with methods in DBConnect class
        dbConnect = new DBConnect(getApplicationContext());

        //initialising the array list to store data which returned from getAllTodos() function
        todoModelList = new ArrayList<>();

        //retrieving the count of records
        int countOfRows = dbConnect.countTodos();

        //retrieving all todos in the db
        todoModelList = dbConnect.getAllTodos();

        //make a reference to the TodoAdapter class
        //TodoAdapter class return each and every single row of todos
        TodoAdapter todoAdapter = new TodoAdapter(this, R.layout.single_todo, todoModelList);
        //setting the adapter to the list view to be displayed
        listView.setAdapter(todoAdapter);

        //setting the count of the todos
        textView.setText("You have " + countOfRows + " todos");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTodo.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TodoModel todoModel = todoModelList.get(position);

                //create alert boxes
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(todoModel.getTitle());
                builder.setMessage(todoModel.getDescription());

                //create buttons
                builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, EditTodo.class);
                        intent.putExtra("id", todoModel.getId());
                        startActivity(intent);
                    }
                });

                builder.setPositiveButton("Finished", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long time = System.currentTimeMillis();
                        todoModel.setFinished(time);
                        dbConnect.updateTodo(todoModel);
                        startActivity(new Intent(context, MainActivity.class));
                    }
                });

                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbConnect.deleteTodo(todoModel.getId());
                        startActivity(new Intent(context, MainActivity.class));
                    }
                });
                builder.show();
            }
        });
    }
}