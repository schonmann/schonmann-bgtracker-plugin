package schonmann.bgtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Anton on 04-Jul-16.
 */
public class BackgroundTrackerReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent myIntent = new Intent(context, GpsTrackService.class);
		context.startService(myIntent);
	}
}
