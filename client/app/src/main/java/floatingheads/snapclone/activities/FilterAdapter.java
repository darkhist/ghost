package floatingheads.snapclone.activities;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by why8222 on 2016/3/17.
 **/

public class FilterAdapter extends BaseAdapter{

    //List<Map<String, Tracker<Face>>> trackerGraphicsList = new ArrayList<Map<String, Tracker<Face>>>();
    //List<Tracker<Face>> trackerGraphics =
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
