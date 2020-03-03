package com.repella.groupproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.repella.groupproject.data.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//Nathan McKnight
//Database Manager
public class DBM extends SQLiteOpenHelper
{
    private static final String DB_NAME = "TaskDB";
    private static final int DB_VERS = 1;
    private static final String TAG = "DBM";

    //Android studio only complains about this because it hates strings outside of a resource file.
    //It compiles fine.
    private static final String[] TABLE_NAMES = new String[]
    {
            "Users",
            "Privileges",
            "User_Task", //bridge table
            "Tasks",
            "Locations"
    };

    public DBM(Context context)
    {
        super(context, DB_NAME, null, DB_VERS);
    }


    //create the DB
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //Note: This method should only run if the database does not exist. Otherwise there will be an error about tables already existing.

        String[] queries = new String[TABLE_NAMES.length];
        String createQuery;

        //privileges table
        createQuery = "CREATE TABLE " + TABLE_NAMES[1] + "(" +
                "priv_id" + " Integer PRIMARY KEY AUTOINCREMENT, " +
                "priv_name" + " Text, " +
                "description" + " Text )";
        queries[0] = createQuery;

        //users table
        createQuery = "CREATE TABLE " + TABLE_NAMES[0] + "(" +
                "user_id" + " Integer PRIMARY KEY AUTOINCREMENT, " +
                "user_name" + " Text, " +
                "password" + " Text, " +
                "priv_id" + " Integer, " +
                "FOREIGN KEY(priv_id) REFERENCES " + TABLE_NAMES[1] + "(priv_id) )";
        queries[1] = createQuery;

        //locations table
        createQuery = "CREATE TABLE " + TABLE_NAMES[4] + "(" +
                "location_id" + " Integer PRIMARY KEY AUTOINCREMENT, " +
                "latitude" + " Real, " +
                "longitude" + " Real, " +
                "Radius" + " Real )";
        queries[2] = createQuery;

        //tasks table
        createQuery = "CREATE TABLE " + TABLE_NAMES[3] + "(" +
                "task_id" + " Integer PRIMARY KEY AUTOINCREMENT, " +
                "task_name" + " Text, " +
                "complete" + " Integer, " +
                "location_id" + " Integer, " +
                "FOREIGN KEY(location_id) REFERENCES " + TABLE_NAMES[4] + "(location_id) " +
                "CHECK(complete == 0 OR complete == 1) )";
        queries[3] = createQuery;

        //user_tasks table
        createQuery = "CREATE TABLE " + TABLE_NAMES[2] + "(" +
                "user_id" + " Integer not null, " +
                "task_id" + " Integer not null, " +
                "FOREIGN KEY(user_id) REFERENCES " + TABLE_NAMES[0] + "(user_id)" +
                "FOREIGN KEY(task_id) REFERENCES " + TABLE_NAMES[0] + "(task_id)" +
                "PRIMARY KEY(user_id, task_id) )";
        queries[4] = createQuery;

        for(int i = 0; i < queries.length; i++)
            sqLiteDatabase.execSQL(queries[i]);

        Log.d(TAG, "OnCreate:: Successfully created the database and its tables.");
    }

    //drop and recreate the DB's tables.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        for(int j = 0; j < TABLE_NAMES.length; j++) //drop DB's tables.
        {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES[i]);
            Log.d(TAG, "OnUpgrade:: Dropping Table: " + TABLE_NAMES[i]);
        }
        onCreate(sqLiteDatabase); //recreate DB's tables.
    }

    public User selectUser(String user_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.query(TABLE_NAMES[0],
                new String[]{"user_name", "password", "priv_id"},
                "user_name = ?",
                new String[]{String.valueOf(user_name)},
                null, null, null, null);

        if(cur != null)
            cur.moveToFirst();
        else return null;

        return new User(cur.getString(0), cur.getString(1), cur.getInt(0));
    }

    //Insert functions
    public void insert(User user) //Tested
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_name", user.getUser_name());
        cv.put("password", user.getPassword());
        cv.put("priv_id", user.getPriv_id());
        db.insert(TABLE_NAMES[0], null, cv);
        Log.d(TAG, "insert::(user) Insert successful.");
    }

    //TODO: Requires entry into the User_Tasks table.
    /*public void insert(com.repella.groupproject.data.Task task, String username) //Untested
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("task_name", task.getTask_name());
        cv.put("complete", task.getComplete());
        cv.put("location_id", task.getLocation_id());
        db.insert(TABLE_NAMES[3], null, cv);
        Log.d(TAG, "insert::(task) Insert successful.");

    }*/

    public void insert(Location loc) //Untested
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("latitude", loc.getLatitude());
        cv.put("longitude", loc.getLongitude());
        cv.put("radius", loc.getRadius());
        db.insert(TABLE_NAMES[4], null, cv);
        Log.d(TAG, "insert::(location) Insert successful.");

    }

    public void insert(Privilege priv) //Tested
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("priv_name", priv.getPriv_name());
        cv.put("description", priv.getDescription());
        db.insert(TABLE_NAMES[1], null, cv);
        Log.d(TAG, "insert::(privilege) Insert successful.");

    }

    //Update functions
    public void update(User user)
    {

    }

    public void update(Task task)
    {

    }

    public void update(Location loc)
    {

    }

    public void update(Privilege priv)
    {

    }

    //Delete functions
    public void delete(User user)
    {

    }

    public void delete(Task task)
    {

    }

    public void delete(Location loc)
    {

    }
    
    public void delete(Privilege priv)
    {

    }

    //Purges entire database's records.
    //yeah. be careful with this and make sure to **comment it out after using it.** otherwise something like a buffer overflow could be exploited to use this function.
    /*public void purge(Context ctx)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String rip = "DROP TABLE IF EXISTS ";
        for(int i = 0; i < TABLE_NAMES.length; i++)
            db.execSQL(rip + TABLE_NAMES[i]);
        Toast.makeText(ctx, "Database has been successfully purged.", Toast.LENGTH_SHORT).show();
        onCreate(db);
    }*/
}
