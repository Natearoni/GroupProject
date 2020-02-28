package com.repella.groupproject;

import java.util.ArrayList;

/**This needs to include the location still*/

public class Task {
    private String taskName;
    private String taskLocation;

    public Task(String name, String location) {

        taskName = name;
        taskLocation = location;
    }

    public String getName() {

        return taskName;
    }

    public String getLocation(){

        return taskLocation;
    }

    //TODO Read In Database Tasks (Could possibly have a retrieve tasks method to pass as parameter)

    public static ArrayList<Task> createTasksList(int numTasks, String name, String location) {
        ArrayList<Task> tasks = new ArrayList<Task>();

        for (int i = 0; i <= numTasks; i++) {
            tasks.add(new Task(i + " Task name " + name, "Location is " + location));
        }

        return tasks;
    }
}