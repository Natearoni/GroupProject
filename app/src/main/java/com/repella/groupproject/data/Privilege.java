package com.repella.groupproject.data;

public class Privilege
{
    private int id = -1;
    private String priv_name;
    private String description;

    public Privilege(String priv_name, String description)
    {
        this.priv_name = priv_name;
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public String getPriv_name()
    {
        return priv_name;
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
