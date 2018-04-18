package floatingheads.snapclone.activities;

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
import floatingheads.snapclone.R;

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
    private boolean usingFront;
    private ImageButton sendButton;
    private ImageButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview);

        sendButton = (ImageButton) findViewById(R.id.btn_send);
        saveButton = (ImageButton) findViewById(R.id.btn_save);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Map to where you want to send the bitmap
                Intent i = new Intent(getApplicationContext(), FriendsActivity.class);
                startActivity(i);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pop up message indicating image saved
            }
        });

        Bitmap bmp = null;
        usingFront = getIntent().getExtras().getBoolean("usingFrontCamera");

        //Getting the bitmap sent in CameraPreviewActivity
        String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mPhotoView = findViewById(R.id.iv_photo);
        //mCurrMatrixTv = findViewById(R.id.tv_current_matrix);

        if(!usingFront){
            Drawable d = new BitmapDrawable(getResources(), bmp);
            mPhotoView.setImageDrawable(d);
        }

        else {

            Matrix matrix = new Matrix();
            matrix.preScale(-1.0f, 1.0f);
            bOut = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            Drawable d = new BitmapDrawable(getResources(), bOut);
            mPhotoView.setImageDrawable(d);

        }





        // Lets attach some listeners, not required though!
        //mPhotoView.setOnMatrixChangeListener(new MatrixChangeListener());
        //mPhotoView.setOnPhotoTapListener(new PhotoTapListener());
        //mPhotoView.setOnSingleFlingListener(new SingleFlingListener());
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
