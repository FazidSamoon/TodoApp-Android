package com.example.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TodoAdapter extends ArrayAdapter<TodoModel> {
    private Context context;
    private int resource;
    private List<TodoModel> todoModelList;

    public TodoAdapter(Context context, int resource, List<TodoModel> todoModels) {
        super(context, resource, todoModels);
        this.context = context;
        this.resource = resource;
        this.todoModelList = todoModels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View row = layoutInflater.inflate(resource, parent , false);

        //accessing textViews and imageView in single_todo.xml file
        TextView title = row.findViewById(R.id.textViewTitle);
        TextView description = row.findViewById(R.id.textViewDescription);
        ImageView tickIcon = row.findViewById(R.id.imageView);

        //retrieving each an every item in arrayList and save them inside TodoModel
        TodoModel todoModel = todoModelList.get(position);
        //replacing text values through the already saved items
        title.setText(todoModel.getTitle());
        description.setText(todoModel.getDescription());
        tickIcon.setVisibility(row.INVISIBLE);

//        if(todoModel.getFinished() > 0) {
//            tickIcon.setVisibility(View.VISIBLE);
//        }

        return row;
    }
}
