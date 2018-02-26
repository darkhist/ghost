package floatingheads.snapclone;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.gms.vision.CameraSource;
import floatingheads.snapclone.ui.CameraSourcePreviewOpenCV;

/**
 * Created by Akira on 2/26/2018.
 */

public class RTImageProcessingTest extends AppCompatActivity{
    // private CameraPreview camPreview;
    private CameraSource mCameraSource = null;
    private ImageView MyCameraPreview = null;
    private CameraSourcePreviewOpenCV mPreview;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Set this APK Full screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Set this APK no title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);}/*
        mPreview = findViewById(R.layout.activity_sign_up);

        //
        // Create my camera preview
        //
        MyCameraPreview = new ImageView(this);

        //commeting this code out -------------
        //SurfaceView camView = new SurfaceView(this);
        //SurfaceHolder camHolder = camView.getHolder();
        //camPreview = new CameraPreview(PreviewSizeWidth, PreviewSizeHeight, MyCameraPreview);

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }

        camHolder.addCallback(camPreview);
        camHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        mPreview = (CameraSourcePreviewOpenCV) findViewById(R.id.frameLayout1);
        mPreview.addView(camView, new FrameLayout.LayoutParams(PreviewSizeWidth, PreviewSizeHeight));
        mPreview.addView(MyCameraPreview, new FrameLayout.LayoutParams(PreviewSizeWidth, PreviewSizeHeight));
    }
*/
    /**
     * Stops the camera.
     *//*
    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop();
    }
*/

}