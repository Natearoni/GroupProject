package com.repella.groupproject.data;

public class Location
{
    private double latitude;
    private double longitude;
    private double radius; //in meters.

    public Location(double lat, double lon, double rad)
    {
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
}
