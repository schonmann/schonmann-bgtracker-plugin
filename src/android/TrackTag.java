package schonmann.bgtracker;

/**
 * Created by Antonio Schönmann on 01/07/2016.
 */

public class TrackTag {
    private String track_id;
    private Integer interval_distance;
    private Integer interval_time;

    public String toString(){
        return track_id;
    }

    public TrackTag(String track_id, Integer interval_distance, Integer interval_time){
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

    public Integer getIntervalDistance() {
        return interval_distance;
    }

    public void setIntervalDistance(Integer interval_distance) {
        this.interval_distance = interval_distance;
    }

    public Integer getIntervalTime() {
        return interval_time;
    }

    public void setIntervalTime(Integer interval_time) {
        this.interval_time = interval_time;
    }
}
