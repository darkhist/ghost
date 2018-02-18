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
//        setContentView(R.layout.activity_view_friends);

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
        RelativeLayout rl = findViewById(R.id.rl_main);

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);

        Button viewFriends = new Button(this);
        viewFriends.setText("View Friends");

        Button addFriends = new Button(this);
        addFriends.setText("Add Friends");

        ll.addView(viewFriends);
        ll.addView(addFriends);

        rl.addView(ll);

        viewFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewFriendsActivity.class);
                startActivity(i);
            }
        });

        addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddFriendsActivity.class);
                startActivity(i);
            }
        });

    }
}
