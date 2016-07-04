package schonmann.bgtracker;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Antonio Schönmann on 02-Jul-16.
 */
public class GetTrackTask extends AsyncTask<JSONArray, Void, Boolean>{

	private static final String TAG = "GetTrackTask";

	private Context context;
	private CallbackContext callbackContext;

	public GetTrackTask(Context context, CallbackContext callbackContext){
		this.context = context;
		this.callbackContext = callbackContext;
	}

	@Override
	protected void onPreExecute(){
	}

	@Override
	protected Boolean doInBackground(JSONArray... params) {
		try{
			String track_id = params[0].getString(0);

			FileInputStream fis = context.openFileInput(track_id);
			Scanner scanner = new Scanner(fis);

			JSONArray jsonArray = new JSONArray();

			while(scanner.hasNextLine()){
				String locationToStr = scanner.nextLine();
				String[] values = locationToStr.split(";");

				JSONObject locationJson = new JSONObject();

				locationJson.put("latitude",  Double.valueOf(values[0]).doubleValue());
				locationJson.put("longitude", Double.valueOf(values[1]).doubleValue());
				locationJson.put("timestamp", Double.valueOf(values[2]).doubleValue());
				locationJson.put("speed",	  Float.valueOf(values[3]).floatValue());

				jsonArray.put(locationJson);
			}

			scanner.close(); fis.close();

			callbackContext.success(jsonArray);

			return true;

		}catch(Exception e){
			Log.d(TAG, "Error.", e);
			callbackContext.error(e.toString());
		}

		return false;
	}

	@Override
	protected void onPostExecute(Boolean success) {
		super.onPostExecute(success);
	}
}
