package com.sourcey.materiallogindemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Helper.DBhelper;
import com.sourcey.materiallogindemo.Helper.TeamStatus;
import com.sourcey.materiallogindemo.Helper.WiFiDirectBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button _checkin;
    Button _chat;
    Button _taskalloc;
    Button _teamstat;
    Button _exit;
    Button _change_wifistatus;
    Button startdiscovery;

    DBhelper myDB;

    String task_owner;
    String status;

    WifiManager wifiManager;
    Channel Channel;
    WifiP2pManager wifiP2pManager;
    private boolean isWifiP2pEnabled = false;

    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SHARED_PREFS_NAME = "name";
    public static final String SHARED_PREFS_LOGGED_IN = "logged_in";
    public static final String SHARED_PREFS_CHECKEDIN_NAME = "checkedin_name";
    public static final String SHARED_PREFS_CHECKEDIN_TIME = "checkedin_time";

    //store list of fetched peers
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();

    private String load_username;
    private boolean logged_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("myTag", "inside oncreate() in main activity");

        loadData();
        checkAndUpdateDB();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String name= getIntent().getExtras().getString("Username");
        textView = findViewById(R.id.textView);
        textView.setText("Welcome " + load_username +" !!!");

        _checkin = findViewById(R.id.btn_checkin);
        _chat = findViewById(R.id.btn_chat);
        _taskalloc = findViewById(R.id.btn_taskalloc);
        _teamstat = findViewById(R.id.btn_teamstat);
        _exit = findViewById(R.id.btn_exit);
        _change_wifistatus = findViewById(R.id.btn_wifi_on_off);
        startdiscovery = findViewById(R.id.btn_discover);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        Channel = wifiP2pManager.initialize(this, getMainLooper(), null);
        broadcastReceiver = new WiFiDirectBroadcastReceiver(wifiP2pManager, Channel, this);
        intentFilter = new IntentFilter();

        // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        _checkin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CheckinActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        _chat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        _taskalloc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TaskAllocationActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        _teamstat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TeamStatusActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        _exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                update_sharedprefs();
            }
        });

        _change_wifistatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeWifiStatus();
            }
        });

        startdiscovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDiscovery();
            }
        });
    }

    private void startDiscovery() {
      //  final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()
      //          .findFragmentById(R.id.frag_list);
      //  fragment.onInitiateDiscovery();

        wifiP2pManager.discoverPeers(Channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Discovery Initiated",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(int reasonCode) {
                Toast.makeText(MainActivity.this, "Discovery Failed : " + reasonCode,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            List<WifiP2pDevice> refreshedPeers = (List<WifiP2pDevice>) peerList.getDeviceList();
            if (!refreshedPeers.equals(peers)) {
                peers.clear();
                peers.addAll(refreshedPeers);
            }

          //  ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();

            if (peers.size() == 0) {
                Log.d("myTag", "No devices found");
                return;
            }
        }
    };

    /** register the BroadcastReceiver with the intent values to be matched */
    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    /*
     Change the WiFi Status
    */
    private void changeWifiStatus() {
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
            _change_wifistatus.setText("WIFI ON");
           // Toast.makeText(this, "Wi-Fi Switched OFF", Toast.LENGTH_SHORT).show();
        } else {
            wifiManager.setWifiEnabled(true);
            _change_wifistatus.setText("WIFI OFF");
          //  Toast.makeText(this, "Wi-Fi Switched ON", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param isWifiP2pEnabled the isWifiP2pEnabled to set
     */
    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }

    private void checkAndUpdateDB() {
        myDB = new DBhelper(this);

        /*
        Fetch entries from Team Status table and check if a corresponding task for the name exists in the Task Allocation table, if true then set Task
        Status as "BUSY" in the Team Status table, else set "FREE"
         */
        Cursor cursor = myDB.getAllNames_TS();

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                task_owner = cursor.getString(0);
               // Toast.makeText(this, "Found names: !!" + task_owner, Toast.LENGTH_SHORT).show();
                Cursor cursor1 = myDB.getSelectedListContents_TA(task_owner);

                int count = cursor1.getCount();

                if (count >= 1){
                    status = "BUSY";
                 }else {
                    status = "FREE";
                }
                boolean updateTask = myDB.updateTaskStatus_TS(task_owner, status);

                if (updateTask == false){
                    Toast.makeText(this, "DB Update Check Fail !!", Toast.LENGTH_SHORT).show();
                }
            }
        }

     //   Cursor cursor1 = myDB.getSelectedListContents_TA(task_owner);

     //   int count = cursor1.getCount();

     //   if (count >= 1){
     //       Toast.makeText(this, "Found rows: !!" + count, Toast.LENGTH_SHORT).show();
     //   }else {
     //       Toast.makeText(this, "no rows found !!", Toast.LENGTH_SHORT).show();
     //   }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("myTag", "inside onCreateOptionsMenu() in main activity");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("myTag", "inside onOptionsItemSelected() in main activity");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } */

    public void loadData() {
        Log.d("myTag", "inside loadData in main activity");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        load_username = sharedPreferences.getString(SHARED_PREFS_NAME, "");
    }

    public void update_sharedprefs() {
        Log.d("myTag", "inside update_sharedprefs in main activity");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(SHARED_PREFS_NAME);
        editor.remove(SHARED_PREFS_LOGGED_IN);
        editor.remove(SHARED_PREFS_CHECKEDIN_NAME);
        editor.remove(SHARED_PREFS_CHECKEDIN_TIME);

        editor.apply();

        finish();
    }

    public void onBackPressed() {
        Log.d("myTag", "inside onbackpressed in main activity");

        finishAffinity();
    }
}
