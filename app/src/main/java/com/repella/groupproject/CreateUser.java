package com.repella.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.repella.groupproject.data.User;

public class CreateUser extends AppCompatActivity {
        public static DBM dbm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        dbm = new DBM(this.getApplicationContext());
        final EditText userName = (EditText) findViewById(R.id.usernameCreate);
        final EditText password = (EditText) findViewById(R.id.passwordCreate);
        Button create = (Button) findViewById(R.id.registerButtonCreate);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User createUser = new User(userName.getText().toString(),password.getText().toString(),1);
                dbm.insert(createUser);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
