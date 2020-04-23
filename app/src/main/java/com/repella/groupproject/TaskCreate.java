package com.repella.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TaskCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        final Intent userLoggedIn = getIntent();
        final Bundle extras = userLoggedIn.getExtras();
        //Log.d("AWAITING", "Awaiting Input");
        //Log.d("TESTNAME", userLoggedIn.getStringExtra("LOGGED_IN_USER"));
        Button confirm = (Button) findViewById(R.id.confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText task = findViewById(R.id.taskDescription);
                EditText location = findViewById(R.id.taskLocation);

                MainActivity.dbm.insert(new com.repella.groupproject.data.Task(task.getText().toString(), 0, 1, location.getText().toString()), MainActivity.dbm.selectUser(extras.getString("LOGGED_IN_USER")).getUser_name());
                //Log.d("GREATSUCCESS", "WE DID IT?");
                finish();
            }
        });
    }
}
