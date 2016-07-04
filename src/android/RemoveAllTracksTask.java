package schonmann.bgtracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.apache.cordova.CallbackContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Anton on 03-Jul-16.
 */

public class RemoveAllTracksTask extends AsyncTask<Void, Void, Boolean> {

	private static final String TAG = "RemoveTrackTask";
	private static final String TRACK_CONTEXTS_FILE = "track_contexts";

	private Context context;
	private CallbackContext callbackContext;

	public RemoveAllTracksTask(Context context, CallbackContext callbackContext){
		this.context = context;
		this.callbackContext = callbackContext;
	}

	@Override
	protected Boolean doInBackground(Void... params) {

		try {
			FileInputStream fis = context.openFileInput(TRACK_CONTEXTS_FILE);
			Scanner scanner = new Scanner(fis);

			List<TrackTag> currentTracks = new ArrayList<TrackTag>();

			while(scanner.hasNextLine()){
				currentTracks.add(new TrackTag(scanner.nextLine()));
			}

			scanner.close();
			fis.close();

			//Delete all track files.

			for(TrackTag track: currentTracks){
				context.deleteFile(track.getTrackId());
			}

			return true;
		}catch(IOException ioe){
			callbackContext.error("Error removing tracks: " + ioe.toString());
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean success) {
		super.onPostExecute(success);
		if(success){
			callbackContext.success("OK");
		}
	}
}