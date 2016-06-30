package schonmann.bgtracker;

import android.util.Log;
import android.content.Intent;
import android.content.Context;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by antonio.alves on 29/06/2016.
 */
public class BackgroundTrackerPlugin extends CordovaPlugin{
    public static final String TAG = "BackgroundTrackerPlugin";
    
    public BackgroundTrackerPlugin() {}
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        Context context = this.cordova.getActivity().getApplicationContext();

        if("startTracking".equals(action)){
            Intent intent = new Intent(context, GpsTrackService.class);
            intent.putExtra("OS_NUMBER", args.getString(0));
            intent.putExtra("TRACK_ID", args.getString(1));
            intent.setAction(action);
            context.startService(intent);
        } else if("stopTracking".equals(action)){
            Intent intent = new Intent(context, GpsTrackService.class);
            context.stopService(intent);
        }

        return true;
    }
}
