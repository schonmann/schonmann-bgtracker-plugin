# Cordova Background Tracker Plugin

Contributors
------------
[@Schonmann](https://github.com/schonmann/)

Background Tracking plugin for Cordova
==========================
This plugin will open a background service to track gps positions even if application is closed.

----------------

Starting a track context:
------
    BackgroundTracker.startTrack(options, 
        callback, 
        fallback);
        
* The "options" argument is a JSON object. Keys: "track_id" (required), "interval_time", "interval_space".

Stop to track:
------
    BackgroundTracker.stopTrack(track_id);
        
* New locations will not be written to that track until it starts again. It will not delete the track file.

Get a persisted track:
------
    BackgroundTracker.getStoredTrack(track_id, function(results){}, function(error){});
        
* Returns all the locations registered for that track as a JSON array.

Remove a persisted track:
------
    BackgroundTracker.removeStoredTrack(track_id, function(){}, function(error){});
        
* Remove the stored track file. Note that this will not stop tracking, and therefore, it will keep writing if started.


