package floatingheads.snapclone.activities;

/**
 * Created by Akira on 2/26/2018.
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import floatingheads.snapclone.R;
import floatingheads.snapclone.androidScreenUtils.Utils;
import floatingheads.snapclone.camera2VisionTools.Clear.ClearOverlay;
import floatingheads.snapclone.camera2VisionTools.Eyes.GooglyEyesFaceTracker;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import floatingheads.snapclone.R;
import floatingheads.snapclone.androidScreenUtils.Utils;
import floatingheads.snapclone.camera2VisionTools.CameraSource;
import floatingheads.snapclone.camera2VisionTools.CameraSourcePreview;
import floatingheads.snapclone.camera2VisionTools.Eyes.GooglyOverlay;

import floatingheads.snapclone.camera2VisionTools.GraphicOverlay;
import floatingheads.snapclone.fragments.ChatFragment;
import floatingheads.snapclone.objects.User;

/**
 * Screen that holds the main camera activity and corresponding buttons
 */
public class CameraPreviewActivity extends AppCompatActivity  {
    private static final String TAG = "Akira Camera";
    private Context context;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int REQUEST_STORAGE_PERMISSION = 201;
    private ImageView ivAutoFocus;

    // CAMERA VERSION ONE DECLARATIONS
    private CameraSource mCameraSource = null;

    // COMMON TO BOTH CAMERAS
    private CameraSourcePreview mPreview;
    private FaceDetector previewFaceDetector = null;
    private GraphicOverlay mGraphicOverlay;
    private boolean wasActivityResumed = false;
    private ImageButton takePictureButton;
    private ImageButton switchButton;
    private ImageButton friendsButton;
    private ImageButton msgsButton;
    private ImageButton filtersButton;
    private FrameLayout mSavedImg;
    private Intent intent;
    private HorizontalScrollView scrollView;

    private Fragment chatFragment;

    //
    User user = new User();

    // FILE STORAGE DECLARATIONS
    private File directory;
    private FileOutputStream fileOut;

    private FaceDetector detector;

    // DEFAULT CAMERA BEING OPENED
    private boolean usingFrontCamera = true;

    private static final int RC_HANDLE_GMS = 9001;

    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

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

        // my code

        if (getIntent().hasExtra("uid")) user.setId(getIntent().getExtras().getInt("uid"));
        if (getIntent().hasExtra("firstName")) user.setFirstName(getIntent().getExtras().getString("firstName"));
        if (getIntent().hasExtra("lastName")) user.setLastName(getIntent().getExtras().getString("lastName"));
        if (getIntent().hasExtra("username")) user.setUsername(getIntent().getExtras().getString("username"));
        if (getIntent().hasExtra("email")) user.setEmail(getIntent().getExtras().getString("email"));

        // end my code

        //decorView = getWindow().getDecorView();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera_preview);
        context = getApplicationContext();

        takePictureButton = (ImageButton) findViewById(R.id.btn_takepicture);
        switchButton = (ImageButton) findViewById(R.id.btn_switch);
        mPreview = (CameraSourcePreview) findViewById(R.id.preview2);
        mGraphicOverlay = (GraphicOverlay) findViewById(R.id.faceOverlay);
        friendsButton = (ImageButton) findViewById(R.id.btn_friends);
        ivAutoFocus = (ImageView) findViewById(R.id.ivAutoFocus);
        msgsButton = (ImageButton) findViewById(R.id.btn_msgs);
        filtersButton = (ImageButton) findViewById(R.id.btn_filters);
        mSavedImg = (FrameLayout) findViewById(R.id.mSavedImg);
        //scrollView = (HorizontalScrollView) findViewById(R.id.ScrollView);

        //scrollView.setVisibility(View.GONE);

        // ViewGroup vg = (ViewGroup)(scrollView.getParent());
        //vg.removeView(scrollView);

        if(checkGooglePlayAvailability()) {
            requestPermissionThenOpenCamera();

            //listener toggle - get rid of need for 2 camerasource view methods
            switchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if (mCameraSource != null) {
                        usingFrontCamera = !usingFrontCamera;
                    }
                    stopCameraSource();
                    createCameraSource(detector);
                }
            });

            mPreview.setOnTouchListener(CameraPreviewTouchListener);

            //Image capture listener
            takePictureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switchButton.setEnabled(false);
                    takePictureButton.setEnabled(false);
                    ivAutoFocus.setEnabled(false);

                    switchButton.setVisibility(View.GONE);
                    takePictureButton.setVisibility(View.GONE);
                    msgsButton.setVisibility(View.GONE);
                    friendsButton.setVisibility(View.GONE);
                    filtersButton.setVisibility(View.GONE);

                    if(mCameraSource != null)
                        mCameraSource.takePicture(cameraSourceShutterCallback, cameraSourcePictureCallback, mPreview);
                }
            });

            // On Friends Button Click - Open Friends Screen
            friendsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), NavBarActivity.class);
                    i.putExtra("uid", user.getId());
                    i.putExtra("firstName", user.getFirstName());
                    i.putExtra("lastName", user.getLastName());
                    i.putExtra("username", user.getUsername());
                    i.putExtra("email", user.getEmail());
                    startActivity(i);
                }
            });

            // On Msg Button Click - Open Chat
            msgsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    if (chatFragment == null) {
                        Bundle userBundle = new Bundle();

                        userBundle.putInt("uid", getIntent().getExtras().getInt("uid"));
                        userBundle.putString("firstName", getIntent().getExtras().getString("firstName"));
                        userBundle.putString("lastName", getIntent().getExtras().getString("lastName"));
                        userBundle.putString("username", getIntent().getExtras().getString("username"));
                        userBundle.putString("email", getIntent().getExtras().getString("email"));

                        chatFragment = new ChatFragment();
                        chatFragment.setArguments(userBundle);
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, chatFragment)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        fragmentManager.beginTransaction().remove(chatFragment).commit();
                    }
                }
            });

            // On Filters Button Click - Open ImageView
            filtersButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = getApplicationContext();
                    GooglyOverlay googly = new GooglyOverlay(usingFrontCamera, mGraphicOverlay);
                    detector = googly.createFaceDetector(context);
                }
            });
        }

        //New Directory path
        String snapCloneDir = Environment.getExternalStorageDirectory()+File.separator+"Pictures"+File.separator+"SnapClone";
        directory = new File(Environment.getExternalStorageDirectory()+File.separator+"Pictures"+File.separator+"SnapClone");
        directory.mkdir();
    }

    /**
     * Callback for camera image capture (deprecated), Takes the image and stores photos in album with the appropriate date
     */
    final CameraSource.PictureCallback cameraSourcePictureCallback = new CameraSource.PictureCallback() {
        @Override
        public void onPictureTaken(Bitmap picture) {
            //mPreview.stop();
            Log.d(TAG, "Taken picture is here!");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ivAutoFocus.setEnabled(true);
                    switchButton.setEnabled(true);
                    takePictureButton.setEnabled(true);
                }
            });

            intent = new Intent(getApplicationContext(), ImageViewActivity.class);
            try {
                //Need to resize the picture to fit the overlay ovre
                picture = getResizedBitmap(picture, 1024);

                //picture Needs to be flipped if using front camera
                if(usingFrontCamera) {
                    Matrix matrix = new Matrix();
                    matrix.preScale(-1.0f, 1.0f);
                    picture = Bitmap.createBitmap(picture, 0, 0, picture.getWidth(), picture.getHeight(), matrix, true);
                }

                //screenshot Filenamelogic
                String fname = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                mPreview.setDrawingCacheEnabled(true);
                Bitmap screenshot = Bitmap.createBitmap(mPreview.getDrawingCache());
                screenshot = getResizedBitmap(screenshot, 1024);
                mPreview.setDrawingCacheEnabled(false);
                //debugging
                Log.d("Screenshot Resolution", "Resolution Width: " + screenshot.getWidth());
                Log.d("Screenshot Resolution", "Resolution Height: " + screenshot.getHeight());

                //overlay method merges the 2 bitmaps into a single image
                screenshot = overlay(picture,screenshot);
                FileOutputStream strm =  context.openFileOutput(fname, Context.MODE_PRIVATE);
                screenshot.compress(Bitmap.CompressFormat.JPEG, 100, strm);

                strm.close();
                screenshot.recycle();
                //SEND bitmap TO IMAGEVIEWACTIVITY
                intent.putExtra("screenshot", fname);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    //Merges 2 bitmaps into a single image
    private Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, new Matrix(), null);
        return bmOverlay;
    }

    //Input the max height, and will output the resized Bitmap image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    final CameraSource.ShutterCallback cameraSourceShutterCallback = new CameraSource.ShutterCallback() {@Override public void onShutter() {Log.d(TAG, "Shutter Callback!");}};

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
                Context context = getApplicationContext();
                ClearOverlay transparent = new ClearOverlay(usingFrontCamera, mGraphicOverlay);
                detector = transparent.createFaceDetector(context);
                createCameraSource(detector);
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

    /**
     * Creates the face detector and the camera.
     */
    private void createCameraSource(FaceDetector detector) {

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
                .setRequestedFps(30.0f)
                .build();

        startCameraSource();
    }




    /**
     * Stops this class' instance of CameraSourcePreview
     */
    private void stopCameraSource() {
        mPreview.stop();
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
            switchButton.setVisibility(View.VISIBLE);
        takePictureButton.setVisibility(View.VISIBLE);
        msgsButton.setVisibility(View.VISIBLE);
        friendsButton.setVisibility(View.VISIBLE);
        filtersButton.setVisibility(View.VISIBLE);
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
