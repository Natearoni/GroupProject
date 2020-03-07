package com.repella.groupproject;

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

    private static final String TAG = "ULND";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing);
        final Intent userLoggedIn = getIntent();
        final Bundle extras = userLoggedIn.getExtras();
        RecyclerView rvTasks = (RecyclerView) findViewById(R.id.recyclerView);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));

        //rvTasks.setItemAnimator(new DefaultItemAnimator());

        String name = extras.getString("USER_NAME");
        Log.d(TAG, "onCreate: " + name + " <--");

        /*if(MainActivity.dbm == null)
        {
            Log.d(TAG, "onCreate: DBM IS NULL. RE-INSTANTIATING?");
            MainActivity.dbm = new DBM(this.getApplicationContext());
        }*/

        User usr = MainActivity.dbm.selectUser(name);
        usr.getUser_name();
        taskList = MainActivity.dbm.selectUserTasks(usr.getUser_name());

        TasksAdapter adapter = new TasksAdapter(taskList);
        Log.d(TAG, "onCreate: " + taskList.get(0).getTask_name());
        Log.d(TAG, "onCreate: count? " + rvTasks.getChildCount());
        rvTasks.setAdapter(adapter);
        Log.d(TAG, "onCreate: countafter? " + rvTasks.getChildCount());
        Log.d(TAG, "onCreate: adp? " + rvTasks.getAdapter().toString());
        //Log.d("ARRAYLIST", Arrays.toString(MainActivity.dbm.selectUserTasks(MainActivity.dbm.selectUser(extras.getString("USER_NAME")).getUser_name()).toArray()));
        //taskList.add("Kitchen", 0, 0);
        //adapter.notifyDataSetChanged();
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

                //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(i);
                finish();
            }
        });
    }
}
