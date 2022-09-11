package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditTodo extends AppCompatActivity {
    Button button;
    EditText title , description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        button = findViewById(R.id.editButton);
        title = findViewById(R.id.editTitle);
        description = findViewById(R.id.editDescription);
    }
}