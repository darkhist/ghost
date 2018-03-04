package floatingheads.snapclone.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;

import floatingheads.snapclone.R;

public class MainActivity extends AppCompatActivity {



    // FILE STORAGE DECLARATIONS
    private File directory;
    private FileOutputStream fileOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String snapCloneDir = Environment.getExternalStorageDirectory()+File.separator+"Pictures"+File.separator+"SnapClone";

        if(!fileExists(this, snapCloneDir)){
        directory = new File(Environment.getExternalStorageDirectory()+File.separator+"Pictures"+File.separator+"SnapClone");
        directory.mkdir();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Button to call OpenCV Camera Activity
        Button cameraInit = findViewById(R.id.openCamera);
        cameraInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),CameraPreviewActivity.class);
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
        Button signup = findViewById(R.id.signUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    public boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }
}