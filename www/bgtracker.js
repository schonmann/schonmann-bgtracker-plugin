var exec = require('cordova/exec');

function BackgroundTracker() {
	console.log("bgtracker.js: is created");
}

function validateStartParams(params){
	return true;
}

function validateStopParams(params){
	return true;
}

BackgroundTracker.prototype.startTracking = function(parameters, callback, fallback){
	
	var service = "BackgroundTrackerPlugin";
	var action = "startTracking";
	var argArray = [parameters];

	function win(winParam){
		if(typeof(callback) === "function"){
			callback();
		}
	}

	function fail(error){
		if(typeof(fallback) === "function"){
			fallback();
		}
	}
	
	if(!validateStartParams(argArray)) return false;
	exec(win, fail, service, action, argArray);
};

BackgroundTracker.prototype.stopTracking = function(trackId, callback, fallback){
	var service = "BackgroundTrackerPlugin";
	var action = "stopTracking";
	var argArray = [trackId];

	function win(winParam){
		if(typeof(callback) === "function"){
			callback();
		}
	}

	function fail(error){
		if(typeof(fallback) === "function"){
			fallback();
		}
	}
	
	if(!validateStopParams(argArray)) return false;
	exec(win, fail, service, action, argArray);
};

var tracker = new BackgroundTracker();
module.exports = tracker;