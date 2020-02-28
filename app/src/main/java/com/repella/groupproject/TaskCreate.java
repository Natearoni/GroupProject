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

        Button confirm = (Button) findViewById(R.id.confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText task = findViewById(R.id.taskDescription);
                EditText location = findViewById(R.id.taskLocation);

                //TODO Add task to database
                //task,location -> Database

                Intent i = new Intent(getApplicationContext(), UserLanding.class);
                startActivity(i);
            }
        });
    }
}
