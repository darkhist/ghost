package com.example.quinnsalas.snapclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button to call OpenCV Camera Activity
        Button cameraInit = findViewById(R.id.openCamera);
        cameraInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),OpenCVCamera.class);
                startActivity(i);
            }
        });

        // On Log In Button Click - Open Log In Screen
        Button login = findViewById(R.id.logIn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        // On Sign Up Button Click - Open Sign Up Screen
        // TODO
    }
}
