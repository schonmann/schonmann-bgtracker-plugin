var exec = require('cordova/exec');

function BackgroundTracker() {
	console.log("bgtracker.js: is created");
}

BackgroundTracker.prototype.startTracking = function(osNumber, trackId){
	exec(function(winParam) {},
			function(error) {
				console.log("'GpsTrackService.class' currently running. Already tracking a displacement!");
			},
			"BackgroundTrackerPlugin",
            "startTracking",
			[osNumber, trackId]);
};
BackgroundTracker.prototype.stopTracking = function(){
	exec(function(winParam) {},
			function(error) {},
			"BackgroundTrackerPlugin",
            "stopTracking",
			[]);
};

var tracker = new BackgroundTracker();
module.exports = tracker;