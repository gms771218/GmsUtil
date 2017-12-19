package com.gms.util.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

import com.gms.util.interfaces.IConnectInterface;

/**
 * Created by gms on 2017/10/24.
 */

public class ConnectObj21 extends ConnectObj {

    public ConnectObj21(Context context, IConnectInterface.IManager manager) {
        super(context, manager);
    }

    @Override
    protected void initService() {

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mContext.registerReceiver(new WifiBroadcastReceiver() , filter) ;
    }

    class WifiBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            mManager.notifyObservers(true,true);
        }
    }


}
