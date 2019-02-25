package com.cs541.abel.contactsapp.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.cs541.abel.contactsapp.Adapters.PersonAdapter;
import com.cs541.abel.contactsapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Person {

    public int id;
    public String name;
    public String phoneNumber;
    public String imagePath;
    public ArrayList<Person> connections;
    private Bitmap pictureBitmap;
    private File file;

    public Person( String name, String phoneNumber, ArrayList<Person> connections, String filePath) {

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.connections = connections;
        this.imagePath = filePath;
    }

    public Person(){
        this.connections = new ArrayList<>();
        this.id = 0;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Bitmap getPictureBitmap() {
        return pictureBitmap;
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

    public void appendConnections(Person person) {

        if(this.connections == null) {
            this.connections = new ArrayList<>();
            this.connections.add(person);
        } else {

            this.connections.add(person);
        }

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
