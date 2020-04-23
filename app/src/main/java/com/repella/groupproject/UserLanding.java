package com.repella.groupproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.repella.groupproject.data.Task;
import com.repella.groupproject.data.User;

import java.util.ArrayList;
import java.util.Arrays;

import static com.repella.groupproject.MainActivity.dbm;

public class UserLanding extends AppCompatActivity {

    private ArrayList<com.repella.groupproject.data.Task> taskList;
    private TasksAdapter adapter;
    private static final String TAG = "ULND";
    private RecyclerView rvTasks;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing);
        final Intent userLoggedIn = getIntent();
        final Bundle extras = userLoggedIn.getExtras();
        rvTasks = findViewById(R.id.recyclerView);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        name = extras.getString("USER_NAME");
        buildRV(); //Builds recycler view.

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.addTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TaskCreate.class);
                i.putExtra("LOGGED_IN_USER", userLoggedIn.getStringExtra("USER_NAME"));
                startActivityForResult(i, 1);
            }
        });

        FloatingActionButton logout = (FloatingActionButton)findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO LOGOUT
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        rvTasks.removeAllViews();
        buildRV();
    }

    private void buildRV()
    {
        User usr = MainActivity.dbm.selectUser(name);
        usr.getUser_name();
        taskList = MainActivity.dbm.selectUserTasks(usr.getUser_name()); //will return null if a user has no tasks.
        if(taskList == null)
        {
            //Log.d(TAG, "onCreate: HALP"); //blank list if someone has no assigned tasks.
            taskList = new ArrayList<>();
        }
        adapter = new TasksAdapter(taskList);
        rvTasks.setAdapter(adapter);
    }
}
