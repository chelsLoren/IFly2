package com.a14541565.chelsey.ifly;

/**
 * Created by Chelsey on 01/08/2017.
 */

public class Photo {

    private String Name;
    private String Description;
    private String Location;
    private int Sync_Status;
    //Add image to database

    Photo(String Name, String Description, String Location, int Sync_Status){
        this.setName(Name);
        this.setDescription(Description);
        this.setLocation(Location);
        this.setSync_Status(Sync_Status);
    }

    public int getSync_Status() {
        return Sync_Status;
    }

    public void setSync_Status(int sync_Status) {
        Sync_Status = sync_Status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
