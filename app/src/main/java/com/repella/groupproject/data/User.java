package com.repella.groupproject.data;

public class User
{
    //ID is auto incremented.
    public String user_name;
    public String password; //must be hashed ideally.
    public int priv_id;

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
}
