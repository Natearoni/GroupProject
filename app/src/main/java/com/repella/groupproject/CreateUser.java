package com.repella.groupproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.repella.groupproject.data.User;

import java.util.regex.Pattern;

public class CreateUser extends AppCompatActivity {
        public static DBM dbm;
        private Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");

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

                if(userName.getText().toString().replaceAll(" ", "").isEmpty() || ((userName.getText().toString().length() > 21) && userName.getText().toString().length() < 1)) {
                    Toast.makeText(getApplicationContext(), "Invalid Username", Toast.LENGTH_LONG).show();
                    finish();
                }
                else if(isValidUsername(userName.getText().toString())) {
                    User createUser = new User(userName.getText().toString(), password.getText().toString(), 1);
                    dbm.insert(createUser);
                    finish();
                }
            }
        });
    }
    private boolean isValidUsername(String username) {
        boolean valid = (username != null) && pattern.matcher(username).matches();
        return valid;
    }
}
