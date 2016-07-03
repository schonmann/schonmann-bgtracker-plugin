var exec = require('cordova/exec');

function BackgroundTracker() {
	console.log("bgtracker.js: is created");
}

BackgroundTracker.prototype.startTracking = function(parameters, callback, fallback){
	
	var service = "BackgroundTrackerPlugin";
	var action = "startTracking";
	var argArray = [parameters];

	function win(winParam){
		callback(winParam);
	}

	function fail(error){
		fallback(error);
	}

	exec(win, fail, service, action, argArray);
};

BackgroundTracker.prototype.stopTracking = function(trackId, callback, fallback){
	var service = "BackgroundTrackerPlugin";
	var action = "stopTracking";
	var argArray = [trackId];

	function win(winParam){
		callback(winParam);
	}

	function fail(error){
		fallback(error);
	}
	
	exec(win, fail, service, action, argArray);
};

BackgroundTracker.prototype.getStoredTrack = function(trackId, callback, fallback){
	var service = "BackgroundTrackerPlugin";
	var action = "getStoredTrack";
	var argArray = [trackId];

	function win(winParam){
		callback(winParam);
	}

	function fail(error){
		fallback(error);
	}
	
	exec(win, fail, service, action, argArray);
};

BackgroundTracker.prototype.removeStoredTrack = function(trackId, callback, fallback){
	var service = "BackgroundTrackerPlugin";
	var action = "removeStoredTrack";
	var argArray = [trackId];

	function win(winParam){
		callback(winParam);
	}

	function fail(error){
		fallback(error);
	}
	
	exec(win, fail, service, action, argArray);
};

BackgroundTracker.prototype.removeAllStoredTracks = function(callback, fallback){
	var service = "BackgroundTrackerPlugin";
	var action = "removeAllStoredTracks";
	var argArray = [];

	function win(winParam){
		callback(winParam);
	}

	function fail(error){
		fallback(error);
	}
	
	exec(win, fail, service, action, argArray);
};

var tracker = new BackgroundTracker();
module.exports = tracker;