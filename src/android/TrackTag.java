package schonmann.bgtracker;

/**
 * Created by antonio.alves on 01/07/2016.
 */
public class TrackTag {
    private String track_id;
    private int interval_distance;
    private int interval_time;

    public TrackTag(String track_id, int interval_distance, int interval_time){
        this.track_id = track_id;
        this.interval_distance = interval_distance;
        this.interval_time = interval_time;
    }

    public TrackTag(String track_id) {
        this.track_id = track_id;
    }

    public String getTrackId() {
        return track_id;
    }

    public void setTrackId(String track_id) {
        this.track_id = track_id;
    }

    public int getIntervalDistance() {
        return interval_distance;
    }

    public void setIntervalDistance(int interval_distance) {
        this.interval_distance = interval_distance;
    }

    public int getIntervalTime() {
        return interval_time;
    }

    public void setIntervalTime(int interval_time) {
        this.interval_time = interval_time;
    }
}
