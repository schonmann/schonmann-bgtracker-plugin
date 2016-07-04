package schonmann.bgtracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.telecom.Call;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Antonio Schönmann on 02-Jul-16.
 */
public class PersistContextTask extends AsyncTask<JSONArray, Void, Boolean> {

	private static final String TAG = "PersistContextTask";
	private static final String TRACK_CONTEXTS_FILE = "track_contexts";

	private Context context;
	private CallbackContext callbackContext;

	public PersistContextTask(Context context, CallbackContext callbackContext){
		this.context = context;
		this.callbackContext = callbackContext;
	}

	@Override
	protected Boolean doInBackground(JSONArray... params) {

		try {
			JSONArray args = params[0];
			JSONObject options = args.getJSONObject(0);

			//Validate given params and build a TrackTag object representing the track.

			if(options.isNull("track_id"))
				throw new JSONException("track_id is null!");

			String track_id = options.getString("track_id");
			TrackTag trackTag = new TrackTag(track_id);

			//Write track in current contexts file for later use by our service.

			String toWrite = trackTag.toString() + "\n";
			FileOutputStream fos = context.openFileOutput(TRACK_CONTEXTS_FILE,
														 Context.MODE_APPEND);
			fos.write(toWrite.getBytes());
			fos.close();

			Log.d(TAG, "Track '" + track_id + "' persisted. Service running in background!");

			return true;

		}catch(Exception e){
			Log.e(TAG, "Error while persisting track!", e);
			callbackContext.error("Error while persisting track!");
		}

		return false;
	}

	@Override
	protected void onPostExecute(Boolean success) {
		super.onPostExecute(success);

		//Finally, trigger tracking service.

		if (success) {
			Intent intent = new Intent(context,
					GpsTrackService.class);
			context.startService(intent);
			callbackContext.success();
		}
	}
}


