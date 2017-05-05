package schonmann.bgtracker;

import android.content.Context;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Antonio Schönmann on 29/06/2016.
 */
 
public class BackgroundTrackerPlugin extends CordovaPlugin{
    public static final String TAG = "BackgroundTrackerPlugin";

    private static final String START_TRACKING = "startTracking";
    private static final String STOP_TRACKING = "stopTracking";
    private static final String GET_STORED_TRACK = "getStoredTrack";
    private static final String REMOVE_STORED_TRACK = "removeStoredTrack";
    private static final String REMOVE_ALL_STORED_TRACKS = "removeAllStoredTracks";

    public BackgroundTrackerPlugin() {
    }

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
	    
	final Context context = this.cordova.getActivity().getApplicationContext();

	if(START_TRACKING.equals(action)){
	    PersistContextTask persistTask = new PersistContextTask(context, callbackContext);
	    persistTask.execute(args);
	} else if(STOP_TRACKING.equals(action)){
	    RemoveContextTask removeTask = new RemoveContextTask(context, callbackContext);
	    removeTask.execute(args);
	} else if(GET_STORED_TRACK.equals(action)){
	    GetTrackTask readTask = new GetTrackTask(context, callbackContext);
	    readTask.execute(args);
	} else if(REMOVE_STORED_TRACK.equals(action)){
	    RemoveTrackTask removeTask = new RemoveTrackTask(context, callbackContext);
	    removeTask.execute(args);
	} else if(REMOVE_ALL_STORED_TRACKS.equals(action)){
	    emoveAllTracksTask removeTask = new RemoveAllTracksTask(context, callbackContext);
	    removeTask.execute();
	}
	    
        return true;
    }
}
