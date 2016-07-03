package schonmann.bgtracker;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;

/**
 * Created by Anton on 03-Jul-16.
 */
public class RemoveTrackTask extends AsyncTask<JSONArray, Void, Boolean> {

	private static final String TAG = "RemoveTrackTask";

	private Context context;
	private CallbackContext callbackContext;

	public RemoveTrackTask(Context context, CallbackContext callbackContext){
		this.context = context;
		this.callbackContext = callbackContext;
	}

	@Override
	protected Boolean doInBackground(JSONArray... params) {

		try{
			String track_id = params[0].getString(0);
			if(context.deleteFile(track_id)){
				callbackContext.success(track_id);
				return true;
			}
		}catch (Exception e){
			Log.d(TAG, "Error.", e);
			callbackContext.error(e.toString());
		}

		return false;
	}
}
