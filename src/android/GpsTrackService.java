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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonio.alves on 29/06/2016.
 */

public class GpsTrackService extends Service{

    private static final String TAG = "GpsTrackService";
    private Location bestLocation = null;

    //Space and time recording intervals.

    private static final float SPACE_INTERVAL = 0; //Space in meters.
    private static final int TIME_INTERVAL = 2000; //Time in millis

    public LocationManager locationManager;
    public MyLocationListener listener;

    //Current target contexts.

    List<TrackTag> currentTrackingContexts;

    public GpsTrackService(){
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    private boolean isStartTrackingIntent(Intent intent){
        return intent != null && intent.getAction() != null && intent.getAction().equals("startTracking");
    }

    private List<TrackTag> readPersistedTracks(){
        List<TrackTag> persistedTracks = new ArrayList<TrackTag>();

        return persistedTracks;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        //Read current tracking contexts stored in device filesystem.

        currentTrackingContexts = readPersistedTracks();

        //If no contexts, stop tracking.

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, TIME_INTERVAL, SPACE_INTERVAL, listener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_INTERVAL, SPACE_INTERVAL, listener);
        }catch(SecurityException se){
            Log.d(TAG, "Security exception.");
            stopSelf();
        }

        if(currentTrackingContexts.isEmpty()){
            stopSelf();
        }

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
            locationManager.removeUpdates(listener);
        }catch(SecurityException se) {
            Log.e(TAG, se.toString());
        }
        Log.d(TAG, "No more tracking contexts. Stopping service...");
    }

    public boolean isBetterLocation(Location newLocation){
        if(bestLocation == null){
            return true;
        }

        boolean hasReasonableDistance;
        boolean hasReasonableTimestamp;

        long timestamp = newLocation.getTime() - bestLocation.getTime();
        float distance = newLocation.distanceTo(bestLocation);

        hasReasonableDistance = distance >= SPACE_INTERVAL;
        hasReasonableTimestamp = timestamp >= TIME_INTERVAL;

        if(hasReasonableTimestamp && hasReasonableDistance)
            return true;

        return false;
    }

    private void persistByTag(LocationDTO location, TrackTag tag){
    }

    private void persistLocation(LocationDTO location){
        for(int i = 0; i < currentTrackingContexts.size(); i++){
            persistByTag(location, currentTrackingContexts.get(i));
        }
    }

    public class MyLocationListener implements LocationListener
    {
        public void onLocationChanged(final Location location)
        {
            Log.d(TAG, "New location! " + location.getLatitude() + " " + location.getLongitude());
            if(isBetterLocation(location)){
                bestLocation = location;
                LocationDTO locationDTO = new LocationDTO(location);
                //persistLocation(locationDTO);
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
}