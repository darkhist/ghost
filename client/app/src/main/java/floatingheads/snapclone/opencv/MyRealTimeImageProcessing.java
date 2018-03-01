package floatingheads.snapclone.opencv;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import floatingheads.snapclone.activities.CVImgProcCameraActivity;
import floatingheads.snapclone.R;





/**
 * Created by Akira on 2/25/2018.
 */

public class MyRealTimeImageProcessing extends Activity
{
    private CVImgProcCameraActivity camPreview;
    private ImageView MyCameraPreview = null;
    private FrameLayout mainLayout;
    private int PreviewSizeWidth = 1280;
    private int PreviewSizeHeight= 960;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
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
        camPreview = new CVImgProcCameraActivity(PreviewSizeWidth, PreviewSizeHeight, MyCameraPreview);

        camHolder.addCallback(camPreview);
        camHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        mainLayout = (FrameLayout) findViewById(R.id.frameLayout1);
        mainLayout.addView(camView, new FrameLayout.LayoutParams(PreviewSizeWidth, PreviewSizeHeight));
        mainLayout.addView(MyCameraPreview, new FrameLayout.LayoutParams(PreviewSizeWidth, PreviewSizeHeight));
    }
    protected void onPause()
    {
        if ( camPreview != null)
            camPreview.onPause();
        super.onPause();
    }
}