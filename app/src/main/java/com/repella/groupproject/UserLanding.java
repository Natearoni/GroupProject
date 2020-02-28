package com.repella.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UserLanding extends AppCompatActivity {

    ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing);

        RecyclerView rvTasks = (RecyclerView) findViewById(R.id.recyclerView);

        // TODO Should retrieve task list from database (See createTasksList method in Task class)

        taskList = Task.createTasksList(20, "Take out trash", "Kitchen");

        TasksAdapter adapter = new TasksAdapter(taskList);
        rvTasks.setAdapter(adapter);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.addTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TaskCreate.class);
                startActivity(i);
            }
        });

        FloatingActionButton logout = (FloatingActionButton)findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO LOGOUT

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
