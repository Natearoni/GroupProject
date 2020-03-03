package com.repella.groupproject.data;

public class Location
{
    private double id = -1;
    private String name;
    private double latitude;
    private double longitude;
    private double radius; //in meters.

    public Location(String name, double lat, double lon, double rad)
    {
        this.name = name;
        latitude = lat;
        longitude = lon;
        radius = rad;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public double getRadius()
    {
        return radius;
    }

    public double getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setId(double id)
    {
        if(this.id == -1)
            this.id = id;
    }
}
