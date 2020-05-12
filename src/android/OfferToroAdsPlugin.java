package com.offertoro.cordova.ad;

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

import com.offertoro.sdk.OTOfferWallSettings;
import com.offertoro.sdk.sdk.OffersInit;

public class OfferToroAdsPlugin extends CordovaPlugin {

  private CallbackContext PUBLIC_CALLBACKS = null;
  private static final String TAG = "OfferToroAdsPlugin";
  private CordovaWebView cWebView;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    cWebView = webView;
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    PUBLIC_CALLBACKS = callbackContext;

    if (action.equals("initOfferToro")) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        public void run() {
          String userID = args.optString(0);
          initOfferToro(userID, PUBLIC_CALLBACKS);
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

  public void initOfferToro(String userID, CallbackContext callbackContext) {
    String YOUR_OW_SECRET_KEY = "6b72f2aaacbd90a3347794490f70e16e"; 
    String YOUR_OW_APP_ID = "9938"; 
    String YOUR_OW_USER_ID = userID;
    
    OTOfferWallSettings.getInstance().configInit(YOUR_OW_APP_ID, YOUR_OW_SECRET_KEY, YOUR_OW_USER_ID);
    OffersInit.getInstance().create(cordova.getActivity());
  }

  public void showOfferWall(CallbackContext callbackContext) { 
    OffersInit.getInstance().showOfferWall(cordova.getActivity());
  }
}
