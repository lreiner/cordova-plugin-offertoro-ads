var cordova = require('cordova');
var exec = require('cordova/exec');

var AdGemAds = {
	initAdGem: function() {
		exec(function(){}, function(){}, "AdGemAdsPlugin", "initAdGem", []);
	},
	initUserID: function(userID) {
		exec(function(){}, function(){}, "AdGemAdsPlugin", "initUserID", [userID]);
	},
	showInterstitial: function() {
		exec(function(){}, function(){}, "AdGemAdsPlugin", "showInterstitial", []);
	},
	showRewardVideo: function() {
		exec(function(){}, function(){}, "AdGemAdsPlugin", "showRewardVideo", []);
	},
	showOfferWall: function() {
		exec(function(){}, function(){}, "AdGemAdsPlugin", "showOfferWall", []);
	}
}

module.exports = AdGemAds;
