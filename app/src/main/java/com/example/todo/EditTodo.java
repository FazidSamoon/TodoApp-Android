package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTodo extends AppCompatActivity {
    private Button button;
    private EditText title , description;
    private DBConnect dbConnect;
    private Context context;
    private long updateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        button = findViewById(R.id.editButton);
        title = findViewById(R.id.editTitle);
        description = findViewById(R.id.editDescription);

        context = this;
        dbConnect = new DBConnect(context);

        final int id = getIntent().getIntExtra("id", 0);
        TodoModel todoModel = dbConnect.getToddById(id);
        title.setText(todoModel.getTitle());
        description.setText(todoModel.getDescription());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleToUpdate = title.getText().toString();
                String descriptionToUpdate = description.getText().toString();
                updateDate = System.currentTimeMillis();

                TodoModel todoModelEdit = new TodoModel(id, titleToUpdate, descriptionToUpdate, updateDate, 0);
                int state = dbConnect.updateTodo(todoModelEdit);
                System.out.println(state);
                startActivity(new Intent(context, MainActivity.class));
            }
        });
    }
}