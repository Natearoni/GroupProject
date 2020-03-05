package com.repella.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.repella.groupproject.data.Privilege;
import com.repella.groupproject.data.User;

public class MainActivity extends AppCompatActivity {

    public static DBM dbm; //database manager -- Nathan

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText userName = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
       // final Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Login Falied", Snackbar.LENGTH_LONG);
        //Create the database.
        dbm = new DBM(this.getApplicationContext());

        //Test Cases:
        //Privilege priv = new Privilege("Normal User", "Access to data/items only the users are allowed to see.");
        //User user = new User("Nathan", "ASDF", 1);
        //dbm.insert(priv);
        //dbm.insert(user);
        //User me = dbm.selectUser("Nathan");
        //Log.d("DBMSelect","" + me.user_name + " " + me.password + " " + me.priv_id);
        //dbm.purge(this.getApplicationContext()); //READ DBM DOCUMENTATION BEFORE PLAYING AROUND WITH THIS.

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
                User userdb= dbm.selectUser(userName.getText().toString());
                if (password.getText().toString().equals(userdb.getPassword())){
                    Intent i = new Intent(getApplicationContext(), UserLanding.class);
                    startActivity(i);
                }else{
                //    mySnackbar.show();
                }


                Intent i = new Intent(getApplicationContext(), UserLanding.class);
                startActivity(i);
            }
        });
    }
}
