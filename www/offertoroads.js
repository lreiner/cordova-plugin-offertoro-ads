var cordova = require('cordova');
var exec = require('cordova/exec');

var OfferToroAds = {
	init: function() {
		exec(function(){}, function(){}, "OfferToroAdsPlugin", "initOfferToro", [userID]);
	},
	showOfferWall: function() {
		exec(function(){}, function(){}, "OfferToroAdsPlugin", "showOfferWall", []);
	}
}

module.exports = OfferToroAds;
