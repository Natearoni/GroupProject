package com.repella.groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.repella.groupproject.data.Task;
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
        dbm.purge(getApplicationContext());

        //Test cases for the DBM: 4/5 Tests. 5th test is within the JUnit test case.
        try
        {
            //Only works for one at a time. Comment/Uncomment out as necessary.
            //testInsertAndSelect();
            //testSqlInjection();
            //injectUserTasks();
            testUpdate();
        }
        catch(Exception e)
        {
            Log.e(TAG, "MainActivityTesting->" + e.getMessage());
        }

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
                        Toast.makeText(getApplicationContext(), "Login Success",
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

    /// Test Case Functions:: \\\

    ///We can't use JUnit for this without a lot of trouble.
    //DBM passes this test.
    private void testInsertAndSelect() throws Exception
    {
        User usr = new User("lumiboi", "lumiboi", 0);

        MainActivity.dbm.insert(usr); //insert new user
        User usr2 = MainActivity.dbm.selectUser("lumiboi"); //attempt to retrieve the user we've inserted.
        if(usr2.getUser_name().compareTo(usr.getUser_name()) != 0 ||
        usr2.getPassword().compareTo(usr.getPassword()) != 0 ||
        usr2.getPriv_id() != usr.getPriv_id())
            throw new Exception("testInsertAndSelect: Test Fail on Select");



        MainActivity.dbm.delete("lumiboi", "User"); //delete the user we inserted
        User usr3 = MainActivity.dbm.selectUser("lumiboi"); //try to select it again
        if(usr3 != null) throw new Exception("testInsertAndSelect: Test Fail on Delete");
        throw new Exception("testInsertAndSelect: Test Success.");
    }

    ///We can't use JUnit for this without a lot of trouble.
    //DBM passes this test.
    private void testSqlInjection() throws Exception
    {
        //String injection = "\' OR 1 = 1;--"; //should return at least 1 record.
        String injection = "non-existent-person' OR 1 = 1;--";

        User usr = new User("lumiboi", "lumiboi", 0); //insert 2 new users
        User usr2 = new User("dimiboi", "dimiboi", 0);
        MainActivity.dbm.insert(usr);
        MainActivity.dbm.insert(usr2);
        User usrInj = MainActivity.dbm.selectUser(injection);
        if(usrInj != null)
            throw new Exception("testSqlInjection: Test Fail");
        MainActivity.dbm.delete(usr.getUser_name(), "User"); //cleanup
        MainActivity.dbm.delete(usr2.getUser_name(), "User");
        throw new Exception("testSqlInjection: Test Success.");
    }
    ///We can't use JUnit for this without a lot of trouble.
    //DBM passes this test as nothing is injected.
    private void injectUserTasks() throws Exception
    {
        User usr = new User("lumiboi", "lumiboi", 0);
        Task tsk = new Task("'; DROP TABLE Users;--", 0, 1, "Benis, Iran");
        //Task tsk = new Task("a", 0, 1, "Benis, Iran");
        dbm.insert(usr);
        dbm.insert(tsk, usr.getUser_name());

        User usr2 = dbm.selectUser("lumiboi"); //should return a result if the table didn't drop.
        if(usr2 == null) throw new Exception("injectUserTasks: Test failed.");

        dbm.delete(usr.getUser_name(), "User"); //cleanup
        dbm.delete(tsk.getTask_name(), "Task");

        throw new Exception("injectUserTasks: Test Success.");
    }

    //Test passed.
    private void testUpdate() throws Exception
    {
        User usr = new User("lumiboi", "lumiboi", 0);
        Task tsk = new Task("'; DROP TABLE Users;--", 0, 1, "Benis, Iran");
        dbm.insert(usr);
        dbm.insert(tsk, usr.getUser_name());
        tsk.setComplete(1);
        dbm.update(tsk.getTask_name(), tsk);
        Task utsk = dbm.selectTask(tsk.getTask_name());
        if(utsk.getComplete() == 0) throw new Exception("testUpdate: Test failed.");
        dbm.delete(usr.getUser_name(), "User"); //cleanup
        dbm.delete(tsk.getTask_name(), "Task");

        throw new Exception("injectUserTasks: Test Success.");
    }
    ///----------------------------------------------\\\
}
