package com.repella.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.repella.groupproject.data.User;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MActivity";
    public static DBM dbm; //database manager -- Nathan McKnight

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText userName = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);

        //Create the database.
        dbm = new DBM(this.getApplicationContext());
        //dbm.purge(getApplicationContext());

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
                //Log.d(TAG, "onClick: " + userName.getText());
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
