package floatingheads.snapclone.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.hardware.Camera.Parameters;

import java.io.IOException;

import floatingheads.snapclone.R;

/*
*  CVImgProcCameraActivity.java
*/
public class CVImgProcCameraActivity extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback
{
    private Camera mCamera = null;
    private Bitmap bitmap = null;
    private int[] pixels = null;
    private byte[] FrameData = null;
    private int imageFormat;
    //private int PreviewSizeWidth;
    //private int PreviewSizeHeight;
    private boolean bProcessing = false;

    //Bringing in ImageView
    private ImageView MyCameraPreview = null;
    private FrameLayout mainLayout;
    private int PreviewSizeWidth;
    private int PreviewSizeHeight;


    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        PreviewSizeWidth = 1280;
        PreviewSizeHeight= 960;

        bitmap = Bitmap.createBitmap(PreviewSizeWidth, PreviewSizeHeight, Bitmap.Config.ARGB_8888);
        pixels = new int[PreviewSizeWidth * PreviewSizeHeight];

        super.onCreate(savedInstanceState);
        //Set this APK Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Set this APK no title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        // Create my camera preview
        MyCameraPreview = new ImageView(this);


        SurfaceView camView = new SurfaceView(this);
        SurfaceHolder camHolder = camView.getHolder();


        camHolder.addCallback(this);
        camHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        mainLayout = (FrameLayout) findViewById(R.id.frameLayout1);
        mainLayout.addView(camView, new FrameLayout.LayoutParams(PreviewSizeWidth, PreviewSizeHeight));
        mainLayout.addView(MyCameraPreview, new FrameLayout.LayoutParams(PreviewSizeWidth, PreviewSizeHeight));
    }
    protected void onPause()
    {
        if ( this != null)
            this.onPause();
        super.onPause();
    }

    @Override
    public void onPreviewFrame(byte[] arg0, Camera arg1)
    {
        // At preview mode, the frame data will push to here.
        if (imageFormat == ImageFormat.NV21)
        {
            //We only accept the NV21(YUV420) format.
            if ( !bProcessing )
            {
                FrameData = arg0;
                mHandler.post(DoImageProcessing);
            }
        }
    }

    /*public void onPause()
    {
        mCamera.stopPreview();
    }*/

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
    {
        Parameters parameters;

        parameters = mCamera.getParameters();
        // Set the camera preview size
        parameters.setPreviewSize(PreviewSizeWidth, PreviewSizeHeight);

        imageFormat = parameters.getPreviewFormat();

        mCamera.setParameters(parameters);

        mCamera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0)
    {
        mCamera = Camera.open();
        try
        {
            // If did not set the SurfaceHolder, the preview area will be black.
            mCamera.setPreviewDisplay(arg0);
            mCamera.setPreviewCallback(this);
        }
        catch (IOException e)
        {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0)
    {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    //
    // Native JNI
    //
    public native boolean ImageProcessing(int width, int height,
                                          byte[] NV21FrameData, int [] pixels);
    static
    {
        System.loadLibrary("OpenCV_cpp_lib");
    }

    private Runnable DoImageProcessing = new Runnable()
    {
        public void run()
        {
            Log.i("MyRTimeImageProcessing", "DoImageProcessing():");
            bProcessing = true;
            ImageProcessing(PreviewSizeWidth, PreviewSizeHeight, FrameData, pixels);

            bitmap.setPixels(pixels, 0, PreviewSizeWidth, 0, 0, PreviewSizeWidth, PreviewSizeHeight);
            MyCameraPreview.setImageBitmap(bitmap);
            bProcessing = false;
        }
    };
}