package com.cs541.abel.contactsapp.Activites;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cs541.abel.contactsapp.Fragments.ContactsFragment;
import com.cs541.abel.contactsapp.R;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final String FILE_NAME = "TaskList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.contacts_action_bar);
        Log.d(TAG, "onCreate: started");
        Fragment contactsFragment =  new ContactsFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, contactsFragment)
                .commit();


    }
}
