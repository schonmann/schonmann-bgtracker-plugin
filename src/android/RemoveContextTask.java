package schonmann.bgtracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

/**
 * Created by Antonio Schönmann on 02-Jul-16.
 */

public class RemoveContextTask extends AsyncTask<JSONArray, Void, Boolean> {

	private static final String TAG = "RemoveContextTask";
	private static final String TRACK_CONTEXTS_FILE = "track_contexts";

	private Context context;
	private CallbackContext callbackContext;

	public RemoveContextTask(Context context, CallbackContext callbackContext){
		this.context = context;
		this.callbackContext = callbackContext;
	}

	@Override
	protected Boolean doInBackground(JSONArray... params) {
		try {
			String track_id = params[0].getString(0);
			TrackTag tag = new TrackTag(track_id);

			FileInputStream fis = context.openFileInput(TRACK_CONTEXTS_FILE);
			String toWrite = "";

			Scanner scanner = new Scanner(fis);

			while(scanner.hasNextLine()){
				String currentLine = scanner.nextLine();
				if(!currentLine.equals(tag.toString())) {
					toWrite += currentLine + "\n";
				}else {
					Log.d(TAG, "Track removed! id => " + tag.getTrackId());
				}
			}

			scanner.close();
			fis.close();

			FileOutputStream fos = context.openFileOutput(TRACK_CONTEXTS_FILE,
														 Context.MODE_PRIVATE);
			fos.write(toWrite.getBytes());
			fos.close();
		}catch(Exception e){
			Log.e(TAG, "Error.", e);
			callbackContext.error("Could not remove task. Already removed?");

			return false;
		}
		return true;
	}

	@Override
	protected void onPostExecute(Boolean success) {
		super.onPostExecute(success);

		//Finally, trigger tracking service.

		if(success){
			Intent intent = new Intent(context, GpsTrackService.class);
			context.startService(intent);

			callbackContext.success();
		}
	}
}
