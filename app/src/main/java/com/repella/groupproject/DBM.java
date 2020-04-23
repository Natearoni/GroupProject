package com.repella.groupproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.repella.groupproject.data.*;
import com.repella.groupproject.data.Task;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//Nathan McKnight
//Database Manager
//All exceptions are thrown to the class using the manager.
public class DBM extends SQLiteOpenHelper
{
    private static final String DB_NAME = "TaskDB"; //database name
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

    /*
        Creating a task:
        Location loc = selectLocation(locationName); //Make sure to insert at least 1 location.
        Task tsk = new Task(taskName, 0, loc.getId()); //^required to use select due to ID not existing until AFTER a select from DB.

        Assigning a user to the task:
            assign(userName, taskName);

        Inserting anything into DB:
            insert(Object data); //where Object is any of the classes in -> com.repella.groupproject.data
     */

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
                "description" + " Text, " +
                "UNIQUE(priv_name) )";
        queries[0] = createQuery;

        //users table
        createQuery = "CREATE TABLE " + TABLE_NAMES[0] + "(" +
                "user_id" + " Integer PRIMARY KEY AUTOINCREMENT, " +
                "user_name" + " Text, " +
                "password" + " Text, " +
                "priv_id" + " Integer, " +
                "FOREIGN KEY(priv_id) REFERENCES " + TABLE_NAMES[1] + "(priv_id)," +
                "UNIQUE(user_name) )";
        queries[1] = createQuery;

        //locations table
        createQuery = "CREATE TABLE " + TABLE_NAMES[4] + "(" +
                "location_id" + " Integer PRIMARY KEY AUTOINCREMENT, " +
                "location_name" + " Text, " +
                "latitude" + " Real, " +
                "longitude" + " Real, " +
                "radius" + " Real, " +
                "UNIQUE(location_name) )";
        queries[2] = createQuery;

        //tasks table
        createQuery = "CREATE TABLE " + TABLE_NAMES[3] + "(" +
                "task_id" + " Integer PRIMARY KEY AUTOINCREMENT, " +
                "task_name" + " Text, " +
                "complete" + " Integer, " +
                "location_id" + " Integer, " +
                "location_name" + " Text, " +
                "FOREIGN KEY(location_id) REFERENCES " + TABLE_NAMES[4] + "(location_id) " +
                "CHECK(complete == 0 OR complete == 1), " +
                "UNIQUE(task_name) )";
        queries[3] = createQuery;

        //user_tasks bridge table
        createQuery = "CREATE TABLE " + TABLE_NAMES[2] + "(" +
                "user_id" + " Integer not null, " +
                "task_id" + " Integer not null, " +
                "FOREIGN KEY(user_id) REFERENCES " + TABLE_NAMES[0] + "(user_id)" +
                "FOREIGN KEY(task_id) REFERENCES " + TABLE_NAMES[3] + "(task_id)" +
                "PRIMARY KEY(user_id, task_id) )";
        queries[4] = createQuery;

        for(int i = 0; i < queries.length; i++)
            sqLiteDatabase.execSQL(queries[i]);

        //Log.d(TAG, "OnCreate:: Successfully created the database and its tables.");
    }

    //drop and recreate the DB's tables.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        for(int j = 0; j < TABLE_NAMES.length; j++) //drop DB's tables.
        {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES[j]);
            Log.d(TAG, "OnUpgrade:: Dropping Table: " + TABLE_NAMES[j]);
        }
        onCreate(sqLiteDatabase); //recreate DB's tables.
    }

    //Select Statements.\\
    //Note: ID in the data structures are only set after a select statement due to auto incrementing primary key -- basically we don't know the ID until its actually inserted\\

    //Testing...
    public Location selectLocationByTaskLocationId(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.query(TABLE_NAMES[4],
                new String[]{"location_id", "location_name", "latitude", "longitude", "radius"},
                "location_id = ?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if(cur != null && cur.moveToFirst())
        {
            Location loc = new Location(cur.getString(cur.getColumnIndex("location_name")),
                    cur.getDouble(cur.getColumnIndex("latitude")),
                    cur.getDouble(cur.getColumnIndex("longitude")),
                    cur.getDouble(cur.getColumnIndex("radius")));
            loc.setId(cur.getInt(cur.getColumnIndex("location_id"))); //0 should be the id.
            return loc;
        }
        else return null;
    }

    //Selects all tasks assigned to a given user.
    public ArrayList<com.repella.groupproject.data.Task> selectUserTasks(String userName)
    {
        ArrayList<com.repella.groupproject.data.Task> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        User usr = selectUser(userName);

        //retrieves all tasks for the given user by id.
        String query = "SELECT DISTINCT task_name FROM " + TABLE_NAMES[3] + " INNER JOIN " + TABLE_NAMES[2] + " ON " + TABLE_NAMES[2]+".user_id = " + usr.getId();

        Cursor cur = db.rawQuery(query, null);

        if(cur != null && cur.moveToFirst())
        {
            do{
                result.add(selectTask(cur.getString(cur.getColumnIndex("task_name"))));
            } while(cur.moveToNext());
            db.close();
        }
        else
        {
            db.close();
            return null;
        }

        db.close();
        return result;
    }

    //Will only ever return 1 user since usernames are unique. (or null)
    public User selectUser(String user_name) //Untested
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.query(TABLE_NAMES[0],
                new String[]{"user_id", "user_name", "password", "priv_id"},
                "user_name = ?",
                new String[]{user_name},
                null, null, null, null);

        if(cur != null && cur.moveToFirst())
        {
            User user = new User(cur.getString(cur.getColumnIndex("user_name")), cur.getString(cur.getColumnIndex("password")), cur.getInt(cur.getColumnIndex("priv_id")));
            user.setID(cur.getInt(cur.getColumnIndex("user_id"))); //0 should be id, 1 should be privilege.
            return user;
        }
        else
        {
            Log.d(TAG, "selectUser: User Not Found! " + user_name);
            return null;
        }

    }

    //Tasks must be defined this way because there is another defintion for Tasks. Probably for multi-threaded java apps.
    //Task names are also unique therefore only 1 task will be returned. (or null)
    public com.repella.groupproject.data.Task selectTask(String task_name) //Untested
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.query(TABLE_NAMES[3],
                new String[]{"task_id", "task_name", "complete", "location_id", "location_name"},
                "task_name = ?",
                new String[]{String.valueOf(task_name)},
                null, null, null, null);

        if(cur != null && cur.moveToFirst())
        {
            com.repella.groupproject.data.Task task = new com.repella.groupproject.data.Task(cur.getString(cur.getColumnIndex("task_name")),
                    cur.getInt(cur.getColumnIndex("complete")),
                    cur.getInt(cur.getColumnIndex("location_id")),
                    cur.getString(cur.getColumnIndex("location_name")));
            task.setId(cur.getInt(cur.getColumnIndex("task_id")));
            return task;
        }
        else return null;
    }

    private Location selectLocation(String location_name) //currently unfunctioning for some reason so made private for now?
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.query(TABLE_NAMES[4],
                new String[]{"location_id", "location_name", "latitude", "longitude", "radius"},
                "location_name = ?",
                new String[]{String.valueOf(location_name)},
                null, null, null, null);

        if(cur != null && cur.moveToFirst())
        {
            Location loc = new Location(cur.getString(cur.getColumnIndex("location_name")),
                    cur.getDouble(cur.getColumnIndex("latitude")),
                    cur.getDouble(cur.getColumnIndex("longitude")),
                    cur.getDouble(cur.getColumnIndex("radius")));
            loc.setId(cur.getInt(cur.getColumnIndex("location_id"))); //0 should be the id.
            return loc;
        }
        else
        {
            Log.d(TAG, "selectLocation: LOCATION IS NULL " + location_name);
            return null;
        }
    }

    public Privilege selectPrivilege(String priv_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.query(TABLE_NAMES[1],
                new String[]{"priv_id", "priv_name", "description"},
                "priv_name = ?",
                new String[]{String.valueOf(priv_name)},
                null, null, null, null);

        if(cur != null && cur.moveToFirst())
        {
            Privilege priv = new Privilege(cur.getString(cur.getColumnIndex("priv_name")),
                    cur.getString(cur.getColumnIndex("description")));
            priv.setId(cur.getInt(cur.getColumnIndex("priv_id"))); //0 should be the id.
            return priv;
        }
        else return null;
    }

    //Assign a user to a task\\
    public void assign(String username, String taskname)
    {
        User usr = selectUser(username);
        com.repella.groupproject.data.Task tsk = selectTask(taskname);

        if(usr == null)
        {
            Log.d(TAG, "assign: User is null. Returning.");
            return;
        }
        if(tsk == null)
        {
            Log.d(TAG, "assign: Task is null. Returning.");
            return;
        }

        //insert into bridge table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", usr.getId());
        cv.put("task_id", tsk.getId());
        db.insert(TABLE_NAMES[2], null, cv);
        db.close();
    }

    public void setTaskCompleted(String taskName, boolean isCompleted)
    {
        int c = isCompleted ? 1 : 0;

        Task tsk = selectTask(taskName);
        Task tskNew = selectTask(taskName); //just to be safe from any pass-by-reference accidents i select the same thing for a new object.
        tskNew.setComplete(c);
        if(tsk == null)
        {
            Log.d(TAG, "setTaskCompleted: Task is null. Returning.");
            return;
        }
        update(tsk, tskNew);
    }

    //Returns all tasks assigned to the given user.
    public ArrayList<com.repella.groupproject.data.Task> selectAssigned(String username) //**IMPORTANT TO TEST THIS ONE as the query is more complex.
    {
        ArrayList<com.repella.groupproject.data.Task> result = new ArrayList();
        User usr = selectUser(username);

        SQLiteDatabase db = this.getWritableDatabase(); //2 = user_task, 3 = tasks
        String query = "SELECT task_name FROM " + TABLE_NAMES[3] +
                " INNER JOIN " + TABLE_NAMES[2] +
                " ON " + TABLE_NAMES[3] + ".task_id = " + TABLE_NAMES[2] + ".task_id " +
                "AND " + TABLE_NAMES[2] + ".user_id = " + usr.getId();

        Cursor cur = db.rawQuery(query,null);

        if(cur != null && cur.moveToFirst())
        {
            do{
                result.add(selectTask(cur.getString(0)));
            } while(cur.moveToNext());
            db.close();
        }
        else
        {
            db.close();
            return null;
        }

        return result;
    }

    public ArrayList<com.repella.groupproject.data.Task> selectAllTasks()
    {
        ArrayList<com.repella.groupproject.data.Task> result = new ArrayList();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT task_name FROM " + TABLE_NAMES[3];

        Cursor cur = db.rawQuery(query,null);

        if(cur != null && cur.moveToFirst())
        {
            do{
                result.add(selectTask(cur.getString(0)));
            } while(cur.moveToNext());
            db.close();
        }
        else
        {
            db.close();
            return null;
        }

        return result;
    }

    //Select all functions\\
    public ArrayList<User> selectAllUsers()
    {
        ArrayList<User> result = new ArrayList();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT user_name FROM " + TABLE_NAMES[0];

        Cursor cur = db.rawQuery(query,null);

        if(cur != null && cur.moveToFirst())
        {
            do{
                result.add(selectUser(cur.getString(0)));
            } while(cur.moveToNext());
            db.close();
        }
        else
        {
            db.close();
            return null;
        }

        return result;
    }

    public ArrayList<Location> selectAllLocations()
    {
        ArrayList<Location> result = new ArrayList();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAMES[4];

        Cursor cur = db.rawQuery(query,null);

        if(cur != null && cur.moveToFirst())
        {
            do{
                Location loc = new Location(
                        cur.getString(cur.getColumnIndex("location_name")),
                        cur.getDouble(cur.getColumnIndex("latitude")),
                        cur.getDouble(cur.getColumnIndex("longitude")),
                        cur.getDouble(cur.getColumnIndex("radius"))
                        );
                loc.setId(cur.getInt(cur.getColumnIndex("location_id")));
                result.add(loc);
            } while(cur.moveToNext());
            db.close();
        }
        else
        {
            db.close();
            return null;
        }

        return result;
    }

    public ArrayList<Privilege> selectAllPrivileges()
    {
        ArrayList<Privilege> result = new ArrayList();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT priv_name FROM " + TABLE_NAMES[1];

        Cursor cur = db.rawQuery(query,null);

        if(cur != null && cur.moveToFirst())
        {
            do{
                result.add(selectPrivilege(cur.getString(0)));
            } while(cur.moveToNext());
            db.close();
        }
        else
        {
            db.close();
            return null;
        }

        return result;
    }

    //Insert functions\\
    public void insert(User user) //Tested
    {
        if(selectUser(user.getUser_name()) != null)
            return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_name", user.getUser_name());
        cv.put("password", user.getPassword());
        cv.put("priv_id", user.getPriv_id());
        db.insert(TABLE_NAMES[0], null, cv);
        db.close();
        //Log.d(TAG, "insert::(user) Insert successful.");
    }

    //Insert a task for a given user by username.
    public void insert(com.repella.groupproject.data.Task task, String username) //Untested
    {
        if(selectTask(task.getTask_name()) != null)
            return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("task_name", task.getTask_name());
        cv.put("complete", task.getComplete());
        cv.put("location_id", task.getLocation_id());
        cv.put("location_name", task.getLocation_name());
        db.insert(TABLE_NAMES[3], null, cv);
        task = selectTask(task.getTask_name()); //select same task so we get an ID

        //assign task to user.
        User assigned = selectUser(username);
        cv = new ContentValues();
        cv.put("user_id", assigned.getId());
        cv.put("task_id", task.getId());
        db.insert(TABLE_NAMES[2], null, cv);
        db.close();
        Log.d(TAG, "insert::(task) Insert successful.");
    }

    public void insert(Location loc) //Untested
    {
        if(selectLocation(loc.getName()) != null)
            return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("location_name", loc.getName());
        cv.put("latitude", loc.getLatitude());
        cv.put("longitude", loc.getLongitude());
        cv.put("radius", loc.getRadius());
        db.insert(TABLE_NAMES[4], null, cv);
        db.close();
        //Log.d(TAG, "insert::(location) Insert successful.");
    }

    public void insert(Privilege priv) //Tested
    {
        if(selectPrivilege(priv.getPriv_name()) != null)
            return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("priv_name", priv.getPriv_name());
        cv.put("description", priv.getDescription());
        db.insert(TABLE_NAMES[1], null, cv);
        db.close();
        //Log.d(TAG, "insert::(privilege) Insert successful.");
    }

    //Update functions\\
    private void update(User uOld, User uNew) //Untested
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //new values
        cv.put("user_name", uNew.getUser_name());
        cv.put("password", uNew.getPassword());
        cv.put("priv_id", uNew.getPriv_id());

        db.update(TABLE_NAMES[0], cv, "user_id = ?", new String[]{""+uOld.getId()});
        db.close();
    }
    public void update(String username, User uNew){ update(selectUser(username), uNew); }

    private void update(com.repella.groupproject.data.Task tOld, com.repella.groupproject.data.Task tNew)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("task_name", tNew.getTask_name());
        cv.put("complete", tNew.getComplete());
        cv.put("location_id", tNew.getLocation_id());

        db.update(TABLE_NAMES[3], cv, "task_id = ?", new String[]{""+tOld.getId()} );
        db.close();
    }
    public void update(String taskOldName, com.repella.groupproject.data.Task taskNew) { update(selectTask(taskOldName), taskNew); }

    private void update(Location lOld, Location lNew)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //new values
        cv.put("location_name", lNew.getName());
        cv.put("latitude", lNew.getLatitude());
        cv.put("longitude", lNew.getLongitude());
        cv.put("radius", lNew.getRadius());

        db.update(TABLE_NAMES[4], cv, "location_id = ?", new String[]{""+lOld.getId()});
        db.close();
    }
    public void update(String locationName, Location lNew){ update(selectLocation(locationName), lNew); }

    private void update(Privilege pOld, Privilege pNew)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //new values
        cv.put("priv_name", pNew.getPriv_name());
        cv.put("description", pNew.getDescription());

        db.update(TABLE_NAMES[1], cv, "priv_id = ?", new String[]{""+pOld.getId()});
        db.close();
    }
    public void update(String privName, Privilege pNew){ update(selectPrivilege(privName), pNew); }

    //Delete functions\\
    //Give this delete function the name field of a particular. The other parameter is for the class name: User, Privilege, Task, Location
    public void delete(String targetName, String className)
    {
        switch(className)
        {
            case "User":
                delete(selectUser(targetName));
                break;
            case "Task":
                delete(selectTask(targetName));
                break;
            case "Location":
                delete(selectLocation(targetName));
                break;
            case "Privilege":
                delete(selectPrivilege(targetName));
                break;
            default:
                Log.d(TAG, "delete: Could not delete because class name is unknown. Returning.");
        }
    }

    private void delete(User user) //Untested
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAMES[0], "user_id = ?", new String[]{""+user.getId()});
        db.delete(TABLE_NAMES[2], "user_id = ?", new String[]{""+user.getId()});
        db.close();
    }

    private void delete(com.repella.groupproject.data.Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAMES[3], "task_id = ?", new String[]{""+task.getId()});
        db.delete(TABLE_NAMES[2], "task_id = ?", new String[]{""+task.getId()});
        db.close();
    }

    private void delete(Location loc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAMES[4], "location_id = ?", new String[]{""+loc.getId()});
        db.close();
    }

    private void delete(Privilege priv)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAMES[3], "priv_id = ?", new String[]{""+priv.getId()});
        db.close();
    }

    //returns # of records from a given table index. Index must fit into TABLE_NAMES above.
    public int getRecordCount(int tableIndex) //Untested
    {
        if(!(tableIndex < TABLE_NAMES.length))
        {
            String count = "SELECT * FROM " + TABLE_NAMES[tableIndex];
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cur = db.rawQuery(count, null);
            return cur.getCount();
        }
        else return -1;
    }

    //Purges entire database's records.
    //yeah. be careful with this and make sure to **comment it out after using it.** otherwise something like a buffer overflow could be exploited to use this function.
    public void purge(Context ctx)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String rip = "DROP TABLE IF EXISTS ";
        for(int i = 0; i < TABLE_NAMES.length; i++)
            db.execSQL(rip + TABLE_NAMES[i]);
        Toast.makeText(ctx, "Database has been successfully purged.", Toast.LENGTH_SHORT).show();
        onCreate(db);
    }
}
