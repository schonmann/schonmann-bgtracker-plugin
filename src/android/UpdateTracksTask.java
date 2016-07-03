package schonmann.bgtracker;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Antonio Schönmann on 02-Jul-16.
 */
public class UpdateTracksTask extends AsyncTask<LocationDTO, Void, Void>{

	private static final String TAG = "UpdateTracksTask";

	private Context context;
	private List<TrackTag> trackContexts;

	public UpdateTracksTask(Context context, List<TrackTag> trackContexts){
		this.context = context;
		this.trackContexts = trackContexts;
	}

	@Override
	protected Void doInBackground(LocationDTO... params) {
		LocationDTO locationDTO = params[0];

		try {
			String toWrite = locationDTO.toString() + "\n";
			Log.d(TAG, "Writing location! => " + locationDTO.toString());

			for(int i = 0; i < trackContexts.size(); i++){
				String fileName = trackContexts.get(i).toString();
				FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
				Log.d(TAG, "Location persisted. File => " + fileName);
				fos.write(toWrite.getBytes());
				fos.close();
			}

		}catch(IOException ioe){
			Log.e(TAG, ioe.getLocalizedMessage());
		}

		return null;
	}
}
