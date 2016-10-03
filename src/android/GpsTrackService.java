package schonmann.bgtracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

/**
 * Created by Antonio Schönmann on 29/06/2016.
 */

public class GpsTrackService extends Service implements LocationListener{

    private static final String TAG = "GpsTrackService";
    private Location bestLocation = null;

    //Space and time recording intervals.

    private static final float SPACE_INTERVAL = 100; //Space in meters.
    private static final int TIME_INTERVAL = 0; //Time in millis

    private LocationManager locationManager;
	private List<TrackTag> currentTrackingContexts;

    //Current target contexts.

    public GpsTrackService(){
    }


    @Override
    public void onCreate(){
        super.onCreate();
    }

    private void gotPersistedContexts(List<TrackTag> persistedContexts){

		//If list is empty, no work to do at all.
        
        if(persistedContexts.isEmpty()){
            stopSelf();
			return;
        }

		Log.d(TAG, "Current tracking contexts: " + persistedContexts.toString());
		this.currentTrackingContexts = persistedContexts;

		//Request location updates to 'this' LocationListener service.

        locationManager = (LocationManager)
				getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates
					(LocationManager.GPS_PROVIDER,
									 TIME_INTERVAL,
									 SPACE_INTERVAL, this);

			locationManager.requestLocationUpdates
					(LocationManager.NETWORK_PROVIDER,
									 TIME_INTERVAL,
									 SPACE_INTERVAL, this);
        }catch(SecurityException se){
            Log.d(TAG, "Security exception.");
            stopSelf();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public int onStartCommand(Intent intent, int flags, int startId){

		Log.d(TAG, "Service called!");

		//As we return START_STICKY, even if the service is killed or
		//the application is closed it will restart when possible.
		//As all the tracking contexts are persisted, the states are
		//recovered when service is restarted.

        Callback onSuccess = new Callback() {
            @Override
            public void onSuccess(Object persistedContexts) {
                gotPersistedContexts((List<TrackTag>) persistedContexts);
            }
        };

		Fallback onFail = new Fallback() {
			@Override
			public void onFail(Object response) {
				stopSelf();
			}
		};

        ReadCurrentContextsTask readTask = new ReadCurrentContextsTask(getApplicationContext(), onSuccess, onFail);
		readTask.execute();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
        try{
            if(locationManager != null)
				locationManager.removeUpdates(this);
        }catch(SecurityException se) {
            Log.e(TAG, se.toString());
        }
        Log.d(TAG, "No more tracking contexts. Stopping service...");
    }

    public boolean isBetterLocation(Location newLocation, Location bestLocation){
        if(bestLocation == null)
            return true;

        boolean hasReasonableDistance;
        boolean hasReasonableTimestamp;

        long timestamp = newLocation.getTime() - bestLocation.getTime();
        float distance = newLocation.distanceTo(bestLocation);

        hasReasonableDistance = distance >= SPACE_INTERVAL;
        hasReasonableTimestamp = timestamp >= TIME_INTERVAL;

        return hasReasonableTimestamp && hasReasonableDistance;
    }

	public void onLocationChanged(final Location location) {

		if(isBetterLocation(location, bestLocation)){
			bestLocation = location;
			UpdateTracksTask UCT = new UpdateTracksTask(getApplicationContext(),
					currentTrackingContexts);
			UCT.execute(new LocationDTO(location));
		}
	}
	@Override
	public void onProviderDisabled(String provider) {
	}
	@Override
	public void onProviderEnabled(String provider) {
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}