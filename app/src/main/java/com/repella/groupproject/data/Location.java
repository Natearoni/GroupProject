package com.repella.groupproject.data;

public class Location
{
    public double latitude;
    public double longitude;
    public double radius;

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
