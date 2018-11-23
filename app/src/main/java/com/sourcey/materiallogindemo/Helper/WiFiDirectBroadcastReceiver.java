package com.sourcey.materiallogindemo.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import com.sourcey.materiallogindemo.MainActivity;

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager WifiP2pManager;
    private WifiP2pManager.Channel Channel;
    MainActivity mainActivity;

    public WiFiDirectBroadcastReceiver(WifiP2pManager WifiP2pManager, WifiP2pManager.Channel Channel, MainActivity mainActivity) {
        this.WifiP2pManager = WifiP2pManager;
        this.Channel = Channel;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
               // activity.setIsWifiP2pEnabled(true);
                mainActivity.setIsWifiP2pEnabled(true);
                Toast.makeText(context, "Wi-Fi P2P Enabled", Toast.LENGTH_SHORT).show();
            } else {
                //activity.setIsWifiP2pEnabled(false);
                mainActivity.setIsWifiP2pEnabled(false);
                Toast.makeText(context, "Wi-Fi P2P Disabled", Toast.LENGTH_SHORT).show();
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            if (WifiP2pManager != null) {
                WifiP2pManager.requestPeers(Channel, mainActivity.peerListListener);
            }
            Log.d("myTAG", "P2P peers changed");
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Connection state changed! We should probably do something about
            // that.
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            //do something
        }
    }
}
