package com.repella.groupproject.data;

public class Task
{
    private int id = -1; //only set during a select statement.
    private String task_name;
    private int complete = 0;
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

    public void setComplete(int complete)
    {
        if(complete == 1 || complete == 0)
            this.complete = complete;
    }

    public int getLocation_id()
    {
        return location_id;
    }

    public String getTask_name()
    {
        return task_name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        if(this.id == -1)
            this.id = id;
    }
}
