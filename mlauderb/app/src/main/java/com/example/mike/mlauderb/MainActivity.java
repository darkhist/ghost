package com.example.mike.mlauderb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set to home screen
        setContentView(R.layout.activity_main);
        // set to friends screen
//        setContentView(R.layout.activity_friends);

//        // Camera button
//        Button cameraInit = findViewById(R.id.cameraInit);
//        cameraInit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(),OpenCVCamera.class);
//                startActivity(i);
//            }
//        });
        // Friends button (temp)
        Button friendsInit = findViewById(R.id.friendsInit);
        friendsInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),FriendsActivity.class);
                startActivity(i);
            }
        });

    }
}
