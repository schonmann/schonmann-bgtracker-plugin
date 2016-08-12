package schonmann.bgtracker;

import android.location.Location;

/**
 * Created by Antonio Schönmann on 01/07/2016.
 */
public class LocationDTO {

    private double latitude;
    private double longitude;
    private long timestamp;
    private float speed;

    public String toString(){
        return latitude + ";" + longitude + ";" + timestamp + ";" + speed;
    }

    public LocationDTO(Location location){
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        timestamp = location.getTime();
        speed = location.getSpeed();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
