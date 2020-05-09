package com.adgem.cordova.ad;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;
import android.app.Activity;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.adgem.android.AdGem;
import com.adgem.android.AdGemCallback;
import com.adgem.android.OfferWallCallback;
import com.adgem.android.PlayerMetadata;

public class AdGemAdsPlugin extends CordovaPlugin {

  private CallbackContext PUBLIC_CALLBACKS = null;
  private static final String TAG = "AdGemAdsPlugin";
  private CordovaWebView cWebView;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    cWebView = webView;
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    PUBLIC_CALLBACKS = callbackContext;

    if (action.equals("initAdGem")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          initAdGem(PUBLIC_CALLBACKS);
        }
      });
      return true;
    }  
    else if (action.equals("initUserID")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          String userID = args.optBoolean(0);
          initUserID(userID, PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    else if (action.equals("showInterstitial")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          showInterstitial(PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    else if (action.equals("showRewardVideo")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          showRewardVideo(PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    else if (action.equals("showOfferWall")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          showOfferWall(PUBLIC_CALLBACKS);
        }
      });
      return true;
    }
    return false;
  }

  public void initAdGem(CallbackContext callbackContext) {
    AdGem adgemads = AdGem.get();

    AdGemCallback callback = new AdGemCallback() {
      @Override
      public void onInterstitialAdStateChanged(int newState) {
        Log.d(TAG, "InterstitialAdStateChanged: " + newState);
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('adgemads.interstitial.statechanged');");
      }
          
      @Override
      public void onRewardedAdStateChanged(int newState) {
        Log.d(TAG, "RewardedAdStateChanged: " + newState);
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('adgemads.rewarded.statechanged');");
      }
  
      @Override
      public void onRewardedAdComplete() {
        // Reward user for watching a rewarded video ad.
        Log.d(TAG, "User finished watching rewarded video ad!");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('adgemads.rewarded.finished');");
      }
    };

    OfferWallCallback callbackOfferWall = new OfferWallCallback() {
      @Override
      public void onOfferWallStateChanged(int newState) {
        Log.d(TAG, "OfferWallStateChanged: " + newState);
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('adgemads.offerwall.statechanged');");
      }

      @Override
      public void onOfferWallReward(int amount) {
        // Notifies that the user has completed an action and should be rewarded with a specified virtual currency amount. 
        Log.d(TAG, "Offerwall Reward for User!: " + amount);
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('adgemads.offerwall.reward');");
      }
      
      @Override
      public void onOfferWallClosed() {
        Log.d(TAG, "Offerwall closed!: ");
        cWebView.loadUrl("javascript:cordova.fireDocumentEvent('adgemads.offerwall.closed');");
      }
    };

    adgemads.registerCallback(callback);
    adgemads.registerOfferWallCallback(callbackOfferWall);
  }

  public void initUserID(String userID,CallbackContext callbackContext) { 
    AdGem adgemads = AdGem.get();

    PlayerMetadata player = new PlayerMetadata.Builder()
    .id(userID)
    .build();
  
    adgem.setPlayerMetaData(player);
  }

  public void showInterstitial(CallbackContext callbackContext) { 
    AdGem adgemads = AdGem.get();
    adgemads.showInterstitialAd(cordova.getActivity());
  }

  public void showRewardVideo(CallbackContext callbackContext) { 
    AdGem adgemads = AdGem.get();
    adgemads.showRewardedAd(cordova.getActivity());
  }

  public void showOfferWall(CallbackContext callbackContext) { 
    AdGem adgemads = AdGem.get();
    adgemads.showOfferWall(cordova.getActivity());
  }
}
