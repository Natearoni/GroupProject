package com.repella.groupproject.data;

public class Privilege
{
    public String priv_name;
    public String description;

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
}
