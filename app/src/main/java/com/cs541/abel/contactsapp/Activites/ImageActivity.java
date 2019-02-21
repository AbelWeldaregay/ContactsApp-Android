package com.cs541.abel.contactsapp.Activites;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cs541.abel.contactsapp.R;

public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();

        Uri imageUri = Uri.parse(intent.getStringExtra("imagePath"));

        this.imageView = findViewById(R.id.imageView2);

        this.imageView.setImageURI(imageUri);


    }



}
