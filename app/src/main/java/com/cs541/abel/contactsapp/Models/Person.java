package com.cs541.abel.contactsapp.Models;

import android.net.Uri;

import java.util.ArrayList;

public class Person {

    private int id;
    private String name;
    private String phoneNumber;
    private Uri imagePath;
    private ArrayList<Person> connections;

    public Person(){}

    public Person(int id, String name, String phoneNumber, Uri imagePath, ArrayList<Person> connections) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.imagePath = imagePath;
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
}
