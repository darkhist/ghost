package floatingheads.snapclone.activities.testing;

/**
 * Created by Akira on 2/26/2018.
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import floatingheads.snapclone.R;
import floatingheads.snapclone.androidScreenUtils.Utils;
import floatingheads.snapclone.camera2VisionTools.Eyes.GooglyFaceTracker;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import floatingheads.snapclone.camera2VisionTools.CameraSource;
import floatingheads.snapclone.camera2VisionTools.CameraSourcePreview;
import floatingheads.snapclone.camera2VisionTools.GraphicFaceTrackerFactory;
import floatingheads.snapclone.camera2VisionTools.GraphicOverlay;

/**
 * Screen that holds the main camera activity and corresponding buttons
 */
public class CameraPreviewActivity extends AppCompatActivity  {
    private static final String TAG = "Akira Camera";
    private Context context;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int REQUEST_STORAGE_PERMISSION = 201;
    private TextView cameraVersion;
    private ImageView ivAutoFocus;

    // CAMERA VERSION ONE DECLARATIONS
    private CameraSource mCameraSource = null;

    // COMMON TO BOTH CAMERAS
    private CameraSourcePreview mPreview;
    private FaceDetector previewFaceDetector = null;
    private GraphicOverlay mGraphicOverlay;
    private boolean wasActivityResumed = false;
    private boolean isRecordingVideo = false;
    private Button takePictureButton;
    private Button switchButton;
    private Button videoButton;
    private int counter;
    private Random rand;

    // FILE STORAGE DECLARATIONS
    private File directory;
    private FileOutputStream fileOut;


    // DEFAULT CAMERA BEING OPENED
    private boolean usingFrontCamera = true;

    private static final int RC_HANDLE_GMS = 9001;

    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    // MUST BE CAREFUL USING THIS VARIABLE.
    // ANY ATTEMPT TO START CAMERA2 ON API < 21 WILL CRASH.
    private boolean useCamera2 = true;

    //Declare variables
    private static final int SELECT_PICTURE = 50;
    ImageView image;

    /**
     * Initializes buttons for the camera screen and provides corresponding functionality to each button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera_preview);
        context = getApplicationContext();
        rand = new Random();


        takePictureButton = (Button) findViewById(R.id.btn_takepicture);
        switchButton = (Button) findViewById(R.id.btn_switch);
//        videoButton = (Button) findViewById(R.id.btn_video);
        mPreview = (CameraSourcePreview) findViewById(R.id.preview2);
        mGraphicOverlay = (GraphicOverlay) findViewById(R.id.faceOverlay);
//        cameraVersion = (TextView) findViewById(R.id.cameraVersion);
        ivAutoFocus = (ImageView) findViewById(R.id.ivAutoFocus);
        counter = 0;

        //New Directory path
        String snapCloneDir = Environment.getExternalStorageDirectory()+File.separator+"Pictures"+File.separator+"SnapClone";

        //listener toggle - get rid of need for 2 camerasource view methods
        switchButton.setOnClickListener(mFlipButtonListener);
        if (savedInstanceState != null) {
            usingFrontCamera = savedInstanceState.getBoolean("IsFrontFacing");
        }

        directory = new File(Environment.getExternalStorageDirectory()+File.separator+"Pictures"+File.separator+"SnapClone");
        directory.mkdir();

        if(checkGooglePlayAvailability()) {
            requestPermissionThenOpenCamera();

//            //Change screens listener
            switchButton.setOnClickListener(new View.OnClickListener() {
                /**
                 * Switches camera view
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    if(usingFrontCamera) {
                        stopCameraSource();
                        createCameraSourceBack();
                        usingFrontCamera = false;
                    } else {
                        stopCameraSource();
                        createCameraSourceFront();
                        usingFrontCamera = true;
                    }
                }
            });

            //Image capture listener
            takePictureButton.setOnClickListener(new View.OnClickListener() {
                /**
                 * Takes picture
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    //update counter;
                    counter = rand.nextInt(1000)+1;
                    switchButton.setEnabled(false);
                    videoButton.setEnabled(false);
                    takePictureButton.setEnabled(false);
                    if(mCameraSource != null)mCameraSource.takePicture(cameraSourceShutterCallback, cameraSourcePictureCallback);
                }
            });

            mPreview.setOnTouchListener(CameraPreviewTouchListener);
        }
    }




    final CameraSource.ShutterCallback cameraSourceShutterCallback = new CameraSource.ShutterCallback() {@Override public void onShutter() {Log.d(TAG, "Shutter Callback!");}};

    /**
     * Callback for camera image capture (deprecated), can set location to save files in this initialization
     */
    final CameraSource.PictureCallback cameraSourcePictureCallback = new CameraSource.PictureCallback() {
        @Override
        public void onPictureTaken(Bitmap picture) {
            Log.d(TAG, "Taken picture is here!");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switchButton.setEnabled(true);
                    videoButton.setEnabled(true);
                    takePictureButton.setEnabled(true);
                }
            });
            FileOutputStream out = null;
            try {
                //Functionality for saving image
                String path = Environment.getExternalStorageDirectory().toString()+File.separator+"Pictures"+File.separator+"SnapClone";
                File file = new File(path, "SnapClone"+counter+".png");
                fileOut = new FileOutputStream(file);
                picture.compress(Bitmap.CompressFormat.JPEG, 95, fileOut);
                fileOut.flush(); // Not really required
                fileOut.close(); // do not forget to close the stream
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            createCameraSource();
            startCameraSource();
        }
    };

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkGooglePlayAvailability() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        if(resultCode == ConnectionResult.SUCCESS) {
            return true;
        } else {
            if(googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(CameraPreviewActivity.this, resultCode, 2404).show();
            }
        }
        return false;
    }

    /**
     * Permissions for Camera features
     */
    private void requestPermissionThenOpenCamera() {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                createCameraSourceFront();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }


        if(previewFaceDetector.isOperational()) {
            previewFaceDetector.setProcessor(new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory(mGraphicOverlay,this.context)).build());
        } else {
            Toast.makeText(context, "FACE DETECTION NOT AVAILABLE", Toast.LENGTH_SHORT).show();
        }
        mCameraSource = new CameraSource.Builder(context, previewFaceDetector)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();

        startCameraSource();

    }

    /**
     *toggle camera source
     */
    private void createCameraSourceBack() {
        previewFaceDetector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .setProminentFaceOnly(true)
                .setTrackingEnabled(true)
                .build();
        if(previewFaceDetector.isOperational()) {
            previewFaceDetector.setProcessor(new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory(mGraphicOverlay,this.context)).build());
        } else {
            Toast.makeText(context, "FACE DETECTION NOT AVAILABLE", Toast.LENGTH_SHORT).show();
        }
        mCameraSource = new CameraSource.Builder(context, previewFaceDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(30.0f)
                .build();
        startCameraSource();

    }

    /**
     * Starts the Camerasource
     */
    final CameraSource.PictureCallback cameraSourcePictureCallback = new CameraSource.PictureCallback() {
        private byte[] byteArray;
        @Override
        //***************************THIS IS WHERE THE "MAGIC" HAPPENS!!!"
        public void onPictureTaken(Bitmap picture) {
            Log.d(TAG, "Taken picture is here!");
            //***************************BUTTONS RUN ON THREAD"
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switchButton.setEnabled(true);
                    videoButton.setEnabled(true);
                    takePictureButton.setEnabled(true);
                }
            });

            FileOutputStream out = null;
            try {
                String filename = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                FileOutputStream stream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                picture.compress(Bitmap.CompressFormat.PNG, 100, stream);

                //Cleanup
                stream.close();
                picture.recycle();

                //pop intent
                Intent intent = new Intent(getApplicationContext(), ImageViewActivity.class);
                intent.putExtra("image", filename);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    };



    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void startCameraSource() {
        if (mCameraSource != null) {
//            cameraVersion.setText("Camera 1");
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }


    }


    /**
     * Autofocus feature functionality
     */
    private final CameraSourcePreview.OnTouchListener CameraPreviewTouchListener = new CameraSourcePreview.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent pEvent) {
            v.onTouchEvent(pEvent);
            if (pEvent.getAction() == MotionEvent.ACTION_DOWN) {
                int autoFocusX = (int) (pEvent.getX() - Utils.dpToPx(60)/2);
                int autoFocusY = (int) (pEvent.getY() - Utils.dpToPx(60)/2);
                ivAutoFocus.setTranslationX(autoFocusX);
                ivAutoFocus.setTranslationY(autoFocusY);
                ivAutoFocus.setVisibility(View.VISIBLE);
                ivAutoFocus.bringToFront();
                if(mCameraSource != null) {
                    mCameraSource.autoFocus(new CameraSource.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean success) {
                            runOnUiThread(new Runnable() {
                                @Override public void run() {ivAutoFocus.setVisibility(View.GONE);}
                            });
                        }
                    });
                } else {
                    ivAutoFocus.setVisibility(View.GONE);
                }
            }
            return false;
        }
    };

    /**
     * Setup Camera permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestPermissionThenOpenCamera();
            } else {
                Toast.makeText(CameraPreviewActivity.this, "CAMERA PERMISSION REQUIRED", Toast.LENGTH_LONG).show();
                finish();
            }
        }
        if(requestCode == REQUEST_STORAGE_PERMISSION) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestPermissionThenOpenCamera();
            } else {
                Toast.makeText(CameraPreviewActivity.this, "STORAGE PERMISSION REQUIRED", Toast.LENGTH_LONG).show();
            }
        }
    }



    /**
     * on resume
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(wasActivityResumed)
            //If the CAMERA2 is paused then resumed, it won't start again unless creating the whole camera again.
            startCameraSource();

    }



    /**
     * On pause
     */
    @Override
    protected void onPause() {
        super.onPause();
        wasActivityResumed = true;
        stopCameraSource();
    }



    /**
     * on destroy
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCameraSource();
        if(previewFaceDetector != null) {
            previewFaceDetector.release();
        }
    }
}
