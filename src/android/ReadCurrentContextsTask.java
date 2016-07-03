package schonmann.bgtracker;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Antonio Schönmann on 02-Jul-16.
 */
public class ReadCurrentContextsTask extends AsyncTask<Void, Void, List<TrackTag>> {

	private static final String TAG = "ReadCurrentContextsTask";
	private static final String TRACK_CONTEXTS_FILE = "track_contexts";

	private Context context;
	private Callback callback;
	private Fallback fallback;

	public ReadCurrentContextsTask(Context context, Callback callback, Fallback fallback) {
		this.context = context;
		this.callback = callback;
		this.fallback = fallback;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected List<TrackTag> doInBackground(Void... params) {
		try {
			FileInputStream fis = context.openFileInput(TRACK_CONTEXTS_FILE);
			Scanner scanner = new Scanner(fis);

			List<TrackTag> lines = new ArrayList<TrackTag>();

			while(scanner.hasNextLine()){
				lines.add(new TrackTag(scanner.nextLine()));
			}

			scanner.close();
			fis.close();

			return lines;
		}catch(IOException ioe){
			Log.e(TAG, "Error.", ioe);
			fallback.onFail(ioe);
			cancel(true);
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<TrackTag> trackTags) {
		super.onPostExecute(trackTags);
		callback.onSuccess(trackTags);
	}
}
