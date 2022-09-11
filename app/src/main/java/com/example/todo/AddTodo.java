package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTodo extends AppCompatActivity {
    private EditText title, description;
    private Button button;
    private DBConnect dbConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        button = findViewById(R.id.button2);

        //creating a DBConnect object to send data which already captured
        dbConnect = new DBConnect(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //capture the value in todoTitle and todoDescription input fields
                String todoTitle = title.getText().toString();
                String todoDescription = description.getText().toString();
                long started = System.currentTimeMillis();


                TodoModel todoModel = new TodoModel(todoTitle, todoDescription, started, 0);

                //send data to the addTodos methods to store in DB
                dbConnect.addTodos(todoModel);

                //redirect to the main screen after saving
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}