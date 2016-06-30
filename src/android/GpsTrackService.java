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

/**
 * Created by antonio.alves on 29/06/2016.
 */

public class GpsTrackService extends Service{

    private static final String TAG = "GpsTrackService";
    private Location bestLocation = null;

    //Space and time intervals.
    private static final float SPACE_INTERVAL = 10;
    private static final int TIME_INTERVAL = 1000 * 60 * 2; //Two minutes.

    public LocationManager locationManager;
    public MyLocationListener listener;

    private List<DisplacementTag> currentDisplacements;

    public GpsTrackService(){
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "onCreate()!");
    }

    private boolean isStartTrackingIntent(Intent intent){
        return intent != null && intent.getAction() != null && intent.getAction().equals("startTracking");
    }

    private List<DisplacementTag> readStartedDisplacements(){
        List<DisplacementTag> displacements = new ArrayList<>();

        // Read and parse displacements from cached file.

        return displacements;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        currentDisplacements = readStartedDisplacements();

        Log.d(TAG, "OS NUMBER => " + serviceOrderNumber);
        Log.d(TAG, "TRACK ID => " + trackId);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
        } catch (SecurityException se) {
            Log.d(TAG, "Security exception.");
            stopSelf();
        }

        Log.d(TAG, "Service started!");

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
        Log.d(TAG, "onDestroy()!");
        try{
            locationManager.removeUpdates(listener);
        }catch(SecurityException se){
            Log.e(TAG, se.toString());
        }
        Log.d(TAG, serviceOrderNumber + ": tracking completed! Saving to local storage...");
        Log.d(TAG, "File created: '/track/"+serviceOrderNumber+"/"+trackId);
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

    public class MyLocationListener implements LocationListener
    {
        public void onLocationChanged(final Location location)
        {
            Log.d(TAG, "New location! " + location.getLatitude() + " " + location.getLongitude());
            if(isBetterLocation(location)){
                bestLocation = location;
            }
        }
        public void onProviderDisabled(String provider)
        {
        }
        public void onProviderEnabled(String provider)
        {
        }
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }
    }
}
