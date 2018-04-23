package floatingheads.snapclone.activities;

import floatingheads.snapclone.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;

import floatingheads.snapclone.ImageViewGestures.OnMatrixChangedListener;
import floatingheads.snapclone.ImageViewGestures.OnPhotoTapListener;
import floatingheads.snapclone.ImageViewGestures.OnSingleFlingListener;
import floatingheads.snapclone.ImageViewGestures.PhotoView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Core;

/**
 * Created by Akira on 4/15/2018.
 */
public class ImageViewActivity extends AppCompatActivity {
    private byte[] byteArray;

    //TESTING IMAGEVIEW
    static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %% ID: %d";
    static final String SCALE_TOAST_STRING = "Scaled to: %.2ff";
    static final String FLING_LOG_STRING = "Fling velocityX: %.2f, velocityY: %.2f";

    //TESTING IMAGEVIEW
    private PhotoView mPhotoView;
    private TextView mCurrMatrixTv;
    private Toast mCurrentToast;
    private Matrix mCurrentDisplayMatrix = null;
    private Bitmap bOut;
    private boolean needs2BeFlipped;
    private ImageButton sendButton;
    private ImageButton saveButton;
    private Bitmap pictureBmp;
    private Bitmap screenshotBmp;
    private Drawable dPicture;
    private Drawable dScreenshot;
    private Mat mat;
    private Mat flipped;

  /*  private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i("OpenCV", "OpenCV loaded successfully");
                    flipped = new Mat();
                    mat = new Mat();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setup Resource Files
        setContentView(R.layout.imageview);
        sendButton = findViewById(R.id.btn_send);
        saveButton = findViewById(R.id.btn_save);
        mPhotoView = findViewById(R.id.iv_photo);

        //Initialize variables
        pictureBmp = null;
        screenshotBmp = null;


        //Getting the screenshot (overlay image) sent from the CameraPreviewActivity
        String fname = getIntent().getStringExtra("screenshot");
        try {
            FileInputStream is2 = this.openFileInput(fname);
            screenshotBmp = BitmapFactory.decodeStream(is2);
            is2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Getting the picture sent from the CameraPreviewActivity
       /* String filename = getIntent().getStringExtra("picture");
        try {
            FileInputStream is = this.openFileInput(filename);
            pictureBmp = BitmapFactory.decodeStream(is);
            is.close();
            } catch (Exception e) {
            e.printStackTrace();
            }*/



        //Check if picture needs to be flipped and get picture
        //needs2BeFlipped = getIntent().getExtras().getBoolean("needs2BeFlipped");

       /* if(needs2BeFlipped){
            mat =new Mat(pictureBmp.getWidth(), pictureBmp.getHeight(), 22);
            flipped = new Mat(pictureBmp.getWidth(), pictureBmp.getHeight(), 22);
            pictureBmp = pictureBmp.copy(Bitmap.Config.ARGB_8888, true);
            Utils.bitmapToMat(pictureBmp, mat);
            Core.flip(mat,flipped,-1);
            Utils.matToBitmap(mat,pictureBmp,true);

            Matrix matrix = new Matrix();
            matrix.preScale(-1.0f, 1.0f);
            pictureBmp = Bitmap.createBitmap(pictureBmp, 0, 0, pictureBmp.getWidth(), pictureBmp.getHeight(), matrix, true);
        }*/

        //Get screenshot
        dScreenshot = new BitmapDrawable(getResources(), screenshotBmp);
        mPhotoView.setImageDrawable(dScreenshot);


        //dScreenshot = new BitmapDrawable(getResources(), screenshotBmp);
        //mPhotoView.setImageDrawable(dScreenshot);



        // Lets attach some listeners, not required though!
        //mPhotoView.setOnMatrixChangeListener(new MatrixChangeListener());
        //mPhotoView.setOnPhotoTapListener(new PhotoTapListener());
        //mPhotoView.setOnSingleFlingListener(new SingleFlingListener());

        //Listener for Send Button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Map to where you want to send the bitmap
                Intent i = new Intent(getApplicationContext(), FriendsActivity.class);
                startActivity(i);
            }
        });

        //Listener for Save Button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pop up message indicating image saved
            }
        });
    }




    private class PhotoTapListener implements OnPhotoTapListener {

        @Override
        public void onPhotoTap(ImageView view, float x, float y) {
            float xPercentage = x * 100f;
            float yPercentage = y * 100f;

            showToast(String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage, view == null ? 0 : view.getId()));
        }
    }

    private void showToast(CharSequence text) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }

        mCurrentToast = Toast.makeText(ImageViewActivity.this, text, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    private class MatrixChangeListener implements OnMatrixChangedListener {
        @Override
        public void onMatrixChanged(RectF rect) {
            mCurrMatrixTv.setText(rect.toString());
        }
    }

    private class SingleFlingListener implements OnSingleFlingListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("PhotoView", String.format(FLING_LOG_STRING, velocityX, velocityY));
            return true;
        }
    }
}
