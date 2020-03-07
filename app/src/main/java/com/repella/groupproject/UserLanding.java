package com.repella.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.repella.groupproject.data.Task;

import java.util.ArrayList;
import java.util.Arrays;

import static com.repella.groupproject.MainActivity.dbm;

public class UserLanding extends AppCompatActivity {

    private ArrayList<com.repella.groupproject.data.Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing);
        final Intent userLoggedIn = getIntent();
        final Bundle extras = userLoggedIn.getExtras();
        RecyclerView rvTasks = (RecyclerView) findViewById(R.id.recyclerView);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        TasksAdapter adapter = new TasksAdapter(taskList);
        rvTasks.setAdapter(adapter);

        taskList = MainActivity.dbm.selectUserTasks(MainActivity.dbm.selectUser(extras.getString("USER_NAME")).getUser_name());
        //Log.d("ARRAYLIST", Arrays.toString(MainActivity.dbm.selectUserTasks(MainActivity.dbm.selectUser(extras.getString("USER_NAME")).getUser_name()).toArray()));
        //taskList.add("Kitchen", 0, 0);
        adapter.notifyDataSetChanged();
//            for(com.repella.groupproject.data.Task i : taskList) {
//                Log.d("TASKS", i.getTask_name() + " || " + String.valueOf(i.getLocation_id()));
//            }
//            if(taskList == null){
//                Log.d("NULL", "Null");
//            }
//            if(taskList.size() == 0) {
//                Log.d("Zero", "Zero");
//            }
//        }
//        catch(Exception e){
//
//        }


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.addTask);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TaskCreate.class);
                i.putExtra("LOGGED_IN_USER", userLoggedIn.getStringExtra("USER_NAME"));
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
