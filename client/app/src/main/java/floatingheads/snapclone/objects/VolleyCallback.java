package floatingheads.snapclone.objects;

import com.android.volley.VolleyError;
import org.json.JSONArray;

/**
 * Created by Mike on 4/6/18.
 */

public interface VolleyCallback {

    void onSuccessResponse(JSONArray result);

    void onErrorResponse(VolleyError volleyError);

}
