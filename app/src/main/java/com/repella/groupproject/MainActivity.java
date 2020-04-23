package com.repella.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.repella.groupproject.data.Location;
import com.repella.groupproject.data.Privilege;
import com.repella.groupproject.data.Task;
import com.repella.groupproject.data.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MActivity";
    public static DBM dbm; //database manager -- Nathan McKnight

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText userName = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
      // final Snackbar mySnackbar = Snackbar.make(findViewById(R.id.), "Login Falied", Snackbar.LENGTH_LONG);


        //Create the database.
        dbm = new DBM(this.getApplicationContext());
        //dbm.purge(getApplicationContext());
        //Test Cases:
        //make some fake data.
        //dbm.insert(new Location("Benis, Iran", 0, 0, 0));
        //dbm.insert(new User("NotADick", "okmaybealittle", 0));
        //dbm.insert(new Task("Go to Bagina, India", 0, 1), "NotADick");

        /*ArrayList<Task> tsks = dbm.selectUserTasks("NotADick");
        for(int i = 0; i < tsks.size(); i++)
            Log.d(TAG, tsks.get(i).getTask_name());*/

        /*ArrayList<Location> locs = dbm.selectAllLocations();
        for(int i = 0; i < locs.size(); i++)
            Log.d(TAG, "onCreate: " + locs.get(i).getId() + " " + locs.get(i).getName());*/



        Button create = (Button) findViewById(R.id.registerButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CreateUser.class);
                startActivity(i);
            }
        });

        final Button login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userName.getText().toString();
                Log.d(TAG, "onClick: " + userName.getText());
                if(username==" ") {
                    Toast.makeText(getApplicationContext(), "You forgot your username",
                            Toast.LENGTH_SHORT).show();

                    //not a very good practice since it will layer activities in this case.
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }else {
                    User userdb = dbm.selectUser(userName.getText().toString());
                    if (password.getText().toString().equals(userdb.getPassword())) {
                        Toast.makeText(getApplicationContext(), "Login Succes",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), UserLanding.class);
                        i.putExtra("USER_NAME", username);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                }
            }
        });
    }

}
