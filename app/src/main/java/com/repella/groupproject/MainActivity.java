package com.repella.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.repella.groupproject.data.Privilege;
import com.repella.groupproject.data.User;

public class MainActivity extends AppCompatActivity {

    public static DBM dbm; //database manager -- Nathan

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the database.
        dbm = new DBM(this.getApplicationContext());

        //Test Cases:
        //Privilege priv = new Privilege("Normal User", "Access to data/items only the users are allowed to see.");
        User user = new User("Nathan", "ASDF", 1);
        //dbm.insert(priv);
        dbm.insert(user);
        User me = dbm.selectUser("Nathan");
        Log.d("DBMSelect","" + me.getUser_name() + " " + me.getPassword() + " " + me.getPriv_id());
        //dbm.purge(this.getApplicationContext()); //READ DBM DOCUMENTATION BEFORE PLAYING AROUND WITH THIS.

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
