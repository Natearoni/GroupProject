package com.repella.groupproject.data;

public class User
{
    //ID is auto incremented.
    private int id = -1; //only set during a select statement.
    private String user_name;
    private String password; //must be hashed ideally.
    private int priv_id;

    public User(String user_name, String password, int priv_id)
    {
        this.user_name = user_name;
        this.password = hash(password);
        this.priv_id = priv_id;
    }

    private String hash(String password)
    {
        return password; //Intentionally left blank for now -- we should probably try and find resources to properly hash this.
    }

    public String getUser_name()
    {
        return user_name;
    }

    public int getPriv_id()
    {
        return priv_id;
    }

    public String getPassword() //already hashed in constructor.
    {
        return password;
    }

    public int getId()
    {
        return id;
    }

    public void setID(int id)
    {
        if(id == -1)
            this.id = id;
    }
}
