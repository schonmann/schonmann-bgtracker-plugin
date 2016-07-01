package schonmann.bgtracker;

import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by antonio.alves on 29/06/2016.
 */
 
public class BackgroundTrackerPlugin extends CordovaPlugin{
    public static final String TAG = "BackgroundTrackerPlugin";

    private static final String START_TRACKING = "startTracking";
    private static final String STOP_TRACKING = "stopTracking";

    public BackgroundTrackerPlugin() {}
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    public void persistTrack(Context context, TrackTag tag) throws IOException{
//        File trackFilesDir = new File(, "track_files");
//        if(!trackFilesDir.exists())
//            trackFilesDir.mkdirs();
//        Log.d(TAG, trackFilesDir.getAbsolutePath());
        File trackFile = new File(context.getExternalFilesDir(null), "track_contexts.txt");
        FileWriter writer = new FileWriter(trackFile);
        writer.append(tag.toString() + "\n");
        writer.flush();
        writer.close();
    }

    public void removeTrackById(String trackId){

    }

    public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        Context context = this.cordova.getActivity().getApplicationContext();

        if(START_TRACKING.equals(action)){
            JSONObject params = args.getJSONObject(0);
            TrackTag trackTag;

            //Get parameters and store in a TrackTag object.

            String track_id = params.getString("track_id");
            trackTag = new TrackTag(track_id);

            //Persist tag in filesystem.
            try {
                persistTrack(context, trackTag);
            }catch(IOException ioe){
                Log.e(TAG, ioe.getLocalizedMessage());
                return false;
            }
        } else {
            //Remove TrackTag from persisted tracks. That means our service will not
            //record gps locations for that file anymore. File should be removed later

            String track_id = args.getString(0);
            removeTrackById(track_id);
        }

        //Trigger a START_STICKY service to track and store user positioning while
        //our cordova application is in background.

        Intent intent = new Intent(context, GpsTrackService.class);
        intent.setAction(action);
        context.startService(intent);

        return true;
    }
}