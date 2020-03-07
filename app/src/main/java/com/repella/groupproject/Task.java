package com.repella.groupproject;

import java.util.ArrayList;

import static com.repella.groupproject.MainActivity.dbm;

/**This needs to include the location still*/

public class Task {
    private String taskName;
    private String taskLocation;
    private boolean isCompleted;
    private DBM dbm;

    public Task(String name, String location, DBM dbm) {

        taskName = name;
        taskLocation = location;
        isCompleted = false;
        this.dbm = dbm;
    }

    public String getName() {

        return taskName;
    }

    public String getLocation() {

        return taskLocation;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    //TODO Read In Database Tasks (Could possibly have a retrieve tasks method to pass as parameter)

    public ArrayList<com.repella.groupproject.data.Task> createTasksList(int numTasks, String name, String location) throws Exception {

        ArrayList<com.repella.groupproject.data.Task> tasks = dbm.selectAllTasks();
        return tasks;
    }
}
