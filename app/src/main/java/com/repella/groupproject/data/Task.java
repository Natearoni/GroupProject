package com.repella.groupproject.data;

public class Task
{
    private String task_name;
    private int complete;
    private int location_id;

    //Throws a fit when complete is not 1 or 0.
    public Task(String name, int complete, int location_id) throws Exception
    {
        task_name = name;
        if(complete == 0 || complete == 1)
            this.complete = complete;
        else throw new Exception("Warning: Task:Task::Complete::: Not a 0 or a 1.");
        this.location_id = location_id;
    }

    public int getComplete()
    {
        return complete;
    }

    public int getLocation_id()
    {
        return location_id;
    }

    public String getTask_name()
    {
        return task_name;
    }
}
