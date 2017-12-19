package com.gms.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gms.util.interfaces.IConnectInterface;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by gms on 2017/10/24.
 */

public class ConnectObj24 extends ConnectObj21 {

    final static String TAG = "ConnectObj24" ;

    // 連線
    public static final int AVAILABLE = 1;
    // 斷線
    public static final int LOST = -1;

    ConnectivityManager connectivityManager;

    public ConnectObj24(Context context, IConnectInterface.IManager manager) {
        super(context, manager);
    }

    @Override
    protected void initService() {
        connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback);
    }


    /**
     * 處理連線狀態物件
     */
    ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        String checkAddress = "8.8.8.8";

        @Override
        public void onAvailable(Network network) {
            super.onAvailable(network);
            // --- 連線 ---
            InetAddress inetAddress = null;
            try {
                inetAddress = network.getByName(checkAddress);
            } catch (UnknownHostException e) {
                Log.e(TAG, e.getMessage());
            } finally {
                // 是否有連上Internet能力
                boolean isSendNotify = isSendNotify(!checkAddress.equals(inetAddress.getHostName()));
                isConnect = true;
                isConnectInternet = !checkAddress.equals(inetAddress.getHostName());
                if (!isConnectInternet)
                    isConnectInternet = ConnectUtil.isConnectNetwork();
                if (isSendNotify) {
                    mHandler.sendEmptyMessage(AVAILABLE);
//                    notifyObservers(isConnect, isConnectInternet);
                }

//                if (isConnectInternet) {
//                    // 有連上Internet能力
//                    notifyObservers(true , isConnectInternet);
//                } else {
//                    notifyObservers(true , isConnectInternet);
//                }
            }
        }

        @Override
        public void onLost(Network network) {
            super.onLost(network);
            // --- 斷線 ---
//            Log.d(TAG, "斷線");
            isConnect = false;
            isConnectInternet = false;
            mHandler.sendEmptyMessage(LOST);
//            notifyObservers(isConnect, isConnectInternet);
        }

        boolean isSendNotify(boolean b) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                return true;
            else {
                return !(isConnectInternet == b);
            }
        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AVAILABLE:
//                    Log.d(TAG, "連線...");
                    mManager.notifyObservers(true, true);
                    break;
                case LOST:
//                    Log.d(TAG, "斷線...");
                    mManager.notifyObservers(true, true);
                    break;
                default:
                    break;

            }
        }
    };

}
