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
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
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


        takePictureButton = (Button) findViewById(R.id.btn_takepicture);
        switchButton = (Button) findViewById(R.id.btn_switch);
        videoButton = (Button) findViewById(R.id.btn_video);
        mPreview = (CameraSourcePreview) findViewById(R.id.preview2);
        mGraphicOverlay = (GraphicOverlay) findViewById(R.id.faceOverlay);
        cameraVersion = (TextView) findViewById(R.id.cameraVersion);
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

            //Image capture listener
            takePictureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
     * Toggles between front-facing and rear-facing modes.
     */
    private View.OnClickListener mFlipButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            usingFrontCamera = !usingFrontCamera;

            if (mCameraSource != null) {
                mCameraSource.release();
                mCameraSource = null;
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
     * When this app is initially installed, we will need to set up a prompt to allow the app to access the user's camera
     * @param requestCode code to request
     * @param permissions description of the requested permissions to be granted
     * @param grantResults result of granted permissions
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
     * Permissions for Camera features
     */
    private void requestPermissionThenOpenCamera() {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //createCameraSourceFront();
                createCameraSource();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }



    /**
     * Starts the Camerasource
     */
    private void startCameraSource() {
        if (mCameraSource != null) {
            cameraVersion.setText("Camera 1");
            try {
                //Graphic Overlay declared
                // When camera source is started
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }

    }



    //==============================================================================================
    // Detector
    //==============================================================================================

    /**
     * Creates the face detector and the camera.
     */
    private void createCameraSource() {
        Context context = getApplicationContext();
        FaceDetector detector = createFaceDetector(context);

        int facing = CameraSource.CAMERA_FACING_FRONT;
        if (!usingFrontCamera) {
            facing = CameraSource.CAMERA_FACING_BACK;
        }

        // For both front facing and rear facing modes, the detector is initialized to do eye landmark classification.
        // We are using fast mode, and tracking 1 face in front camera view, and multiple faces in rear camera view.
        // Setting PromentFaceOnly as true when usingFrontCamera will stop scanning for faces when single largest face is found
        // The former results in greater efficiency, we also increase minfacesize from default for further optimizations
        mCameraSource = new CameraSource.Builder(context, detector)
                .setFacing(facing)
                .setRequestedPreviewSize(320, 240)
                .setRequestedFps(60.0f)
                .build();
    }



    /**
     * Callback for camera image capture (deprecated), Takes the image and stores photos in album with the appropriate date
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

    /**
     * Stops this class' instance of CameraSourcePreview
     */
    private void stopCameraSource() {
        mPreview.stop();
    }


    /**
     * Creates the face detector and associated processing pipeline to support either front facing
     * mode or rear facing mode.  Checks if the detector is ready to use, and displays a low storage
     * warning if it was not possible to download the face library.
     */
    @NonNull
    private FaceDetector createFaceDetector(Context context) {
        // For both front facing and rear facing modes, the detector is initialized to do eye landmark classification.
        // We are using fast mode, and tracking 1 face in front camera view, and multiple faces in rear camera view.
        // Setting PromentnFaceOnly as true when usingFrontCamera will stop scanning for faces when single largest face is found
        // The former results in greater efficiency, we also increase minfacesize from default for further optimizations
        FaceDetector detector = new FaceDetector.Builder(context)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setTrackingEnabled(true)
                .setMode(FaceDetector.FAST_MODE)
                .setProminentFaceOnly(usingFrontCamera)
                .setMinFaceSize(usingFrontCamera ? 0.35f : 0.15f)
                .build();

        Detector.Processor<Face> processor;
        if (usingFrontCamera) {
            // For front facing mode, a single tracker instance is used with an associated focusing processor
            Tracker<Face> tracker = new GooglyFaceTracker(mGraphicOverlay);
            processor = new LargestFaceFocusingProcessor.Builder(detector, tracker).build();
        } else {
            // For rear facing mode, a factory is used to create per-face tracker instances.  A
            // tracker is created for each face and is maintained as long as the same face is
            // visible, enabling per-face state to be maintained over time.  This is used to store
            // the iris position and velocity for each face independently, simulating the motion of
            // the eyes of any number of faces over time.
            MultiProcessor.Factory<Face> factory = new MultiProcessor.Factory<Face>() {
                @Override
                public Tracker<Face> create(Face face) {
                    return new GooglyFaceTracker(mGraphicOverlay);
                }
            };
            processor = new MultiProcessor.Builder<>(factory).build();
        }

        detector.setProcessor(processor);

        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            Log.w(TAG, "Face detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowStorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowStorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }
        return detector;
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
