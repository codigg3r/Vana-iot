package com.example.codigger.vana;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class dash extends AppCompatActivity {
    WifiManager wifiManager;
    List<WifiConfiguration> configuredNetworks;
    public FirebaseAuth mAuth;
    String[] SSIDs ;
    String currentWifi ;
    String key;
    String nodeSsid = "vana";
    String nodeKey  = "vana_key11";
    EditText eTKey;
    TextView tWWifi;
    Button baglan;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        mAuth = FirebaseAuth.getInstance();
        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        eTKey = findViewById(R.id.eTKey);
        tWWifi = findViewById(R.id.tWWifi);
        baglan = findViewById(R.id.baglan);
        pb = findViewById(R.id.progressBar);
        //set wifi enable
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

    }

    // BUTTON ONCLICK METHOD
    @SuppressLint("NewApi")
    public void run(View btn){
        getNetworks(wifiManager);
        ListAdapter wifiList = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,SSIDs);
        ListView wifiView =  findViewById(R.id.resWifi);
        wifiView.setVisibility(View.VISIBLE);
        setVisibility(0);
        wifiView.setAdapter(wifiList);
        onClickItem(wifiView);
        pb.setProgress(30,true);
    }

    public void onClickItem(final ListView mList){
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setCurrentWifi(String.valueOf(parent.getItemAtPosition(position)));
            mList.setVisibility(View.GONE);
            setVisibility(1);
            tWWifi.setText(currentWifi);
            pb.setProgress(40,true);
            }

        });

    }

    public void setVisibility(int id){
        if (id == 1) {
            eTKey.setVisibility(View.VISIBLE);
            tWWifi.setVisibility(View.VISIBLE);
            baglan.setVisibility(View.VISIBLE);
        }else{
            eTKey.setVisibility(View.GONE);
            tWWifi.setVisibility(View.GONE);
            baglan.setVisibility(View.GONE);
        }
    }

    public void setCurrentWifi(String mwifi){
        currentWifi = mwifi;
    }

    //CONNECT WiFi
    public void connectWifi(final String ssid, String key) {
            int netId = getNetworks(wifiManager, ssid);
            Log.d("puta","id: "+netId);
            if (netId == -1){
                netId = wifiManager.addNetwork(setWifiConfig(ssid,key));
            }else{
                wifiManager.removeNetwork(netId);
                netId = wifiManager.addNetwork(setWifiConfig(ssid,key));

            }

            wifiManager.disconnect();
            wifiManager.enableNetwork(netId, true);
            wifiManager.reconnect();

            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean res = wifiManager.getConnectionInfo().getSSID().equals(ssid);
                    Log.d("puta","CONNECTION"+wifiManager.getConnectionInfo().toString());
                    if (res){
                        Toast.makeText(dash.this, "Bağlantı Hatası !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(dash.this, "Bağlantı Başarılı !", Toast.LENGTH_SHORT).show();
                    }
                }
            },3000);

    }

    // SCAN NETWORKS WITH SSID
    public int getNetworks(WifiManager wifiManager, String ssid){
        int id = -1;
        configuredNetworks = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration conf : configuredNetworks){
            if (conf.SSID.equals(String.format("\"%s\"", ssid))){
                id = (conf.networkId);
                return id;
            }
        }
        return -1;
    }
    // SCAN NETWORKS
    public void getNetworks(WifiManager wifiManager){
        configuredNetworks = wifiManager.getConfiguredNetworks();
        int size = configuredNetworks.size();
        int indexSSID = 0;
        SSIDs = new String[size];
        for (WifiConfiguration conf : configuredNetworks){

            SSIDs[indexSSID]= conf.SSID.split("\"")[1];
            indexSSID += 1;
        }
    }

    // CREATE WiFi CONFiG
    public WifiConfiguration setWifiConfig(String ssid,String key){
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", ssid);
        wifiConfig.preSharedKey = String.format("\"%s\"", key);

        return wifiConfig;
    }
    // CONNECT BUTTON ONCLICK
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void connect(View view) {
        key = eTKey.getText().toString();
        connectWifi(currentWifi,key);
        pb.setProgress(70,true);
        setVisibility(0);
        // connectWifi(nodeSsid,nodeKey);
        sendData();
    }

    private void sendData() {


    }

    public void logOut(View view) {
        mAuth.signOut();
        Intent dashIntent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(dashIntent);
        finish();
    }
}

