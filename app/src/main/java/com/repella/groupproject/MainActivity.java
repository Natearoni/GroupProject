package com.repella.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button create = (Button) findViewById(R.id.registerButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CreateUser.class);
                startActivity(i);
            }
        });

        Button login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Authenticate user/password combo is accurate...

                Intent i = new Intent(getApplicationContext(), UserLanding.class);
                startActivity(i);
            }
        });
    }
}
