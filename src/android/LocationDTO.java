package schonmann.bgtracker;

import android.location.Location;

/**
 * Created by antonio.alves on 01/07/2016.
 */
public class LocationDTO {

    private double latitude;
    private double longitude;
    private double timestamp;
    private float speed;

    public LocationDTO(Location location){
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        timestamp = location.getTime();
        speed = location.getTime();
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

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}