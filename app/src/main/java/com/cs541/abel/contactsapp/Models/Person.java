package com.cs541.abel.contactsapp.Models;

import android.net.Uri;
import com.cs541.abel.contactsapp.Adapters.PersonAdapter;
import java.util.ArrayList;

public class Person {

    public int id;
    public String name;
    public String phoneNumber;
    public Uri imagePath;
    public ArrayList<Person> connections;

    public Person(){}

    public Person(int id, String name, String phoneNumber, Uri imagePath, ArrayList<Person> connections) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.imagePath = imagePath;
        this.connections = connections;
    }

    public Person( String name, String phoneNumber, ArrayList<Person> connections) {

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.connections = connections;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setImagePath(Uri imagePath) {
        this.imagePath = imagePath;
    }

    public void setConnections(ArrayList<Person> connections) {
        this.connections = connections;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Uri getImagePath() {
        return imagePath;
    }

    public ArrayList<Person> getConnections() {
        return connections;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(!Person.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final Person other = (Person) obj;

        if(other.phoneNumber.equals(this.phoneNumber) && other.name.equals(this.name)
                && this.connections.equals(other.connections)) {
            return true;
        }
        else {
            return false;
        }

    }

}
