package com.gms.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.util.Log;

import com.gms.util.interfaces.IConnectInterface;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by gms on 2017/7/24.
 */

public class AppConnectManager implements IConnectInterface.IManager {

    static final String TAG = "AppConnectManager";

    /**
     * 連線狀態
     */
    @IntDef({CONNECT_STATUS_UNKNOWN, CONNECT_STATUS_NOT_CONNECTION, CONNECT_STATUS_CONNECTION, CONNECT_STATUS_CONNECTION_NETWORK})
    public @interface ConnectStatus {
    }

    public final static int CONNECT_STATUS_UNKNOWN = 0;
    public final static int CONNECT_STATUS_NOT_CONNECTION = -1;
    public final static int CONNECT_STATUS_CONNECTION = 1;
    public final static int CONNECT_STATUS_CONNECTION_NETWORK = 2;

    // 連線
    public static final int AVAILABLE = 1;
    // 斷線
    public static final int LOST = -1;

    private int isConnectNetwork = CONNECT_STATUS_UNKNOWN;

    private boolean isConnectInternet = false;

    private boolean isConnect = false;

    ArrayList<IConnectInterface.IObserver> observers = new ArrayList<IConnectInterface.IObserver>();

    static AppConnectManager instance = null;
    Context mContext;
    ConnectivityManager connectivityManager;

    AppConnectManager(Context context) {
        mContext = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static void init(Context context) {
        instance = new AppConnectManager(context);
    }

    public static AppConnectManager getInstance() {
        if (instance == null)
            throw new RuntimeException("call AppConnectManager.init()");
        return instance;
    }

    @Override
    public void registerObserver(IConnectInterface.IObserver observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    @Override
    public void unRegisterObserver(IConnectInterface.IObserver observer) {
        if (observers.contains(observer))
            observers.remove(observer);
    }

    @Override
    public void notifyObservers(boolean isConnect, boolean isInternet) {
        for (IConnectInterface.IObserver observer : observers)
            observer.update(getConnectStatus());
//            observer.update(this, isConnect, isInternet);
    }

    @Override
    public void onStart() {
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback);
    }

    @Override
    public void onStop() {
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }



    public void setConnectStatus(@ConnectStatus int value) {
        isConnectNetwork = value ;
    }

    @Override
    public int getConnectStatus() {
        return isConnectNetwork;
    }

    public boolean isConnectInternet() {
        return isConnectInternet;
    }

    public boolean isConnect() {
        return isConnect;
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
//                Log.d(TAG, String.format("isConnect:%b , isConnectInternet:%b , isSendNotify:%b", isConnect, isConnectInternet, isSendNotify));
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
                    notifyObservers(isConnect, isConnectInternet);
                    break;
                case LOST:
//                    Log.d(TAG, "斷線...");
                    notifyObservers(isConnect, isConnectInternet);
                    break;
                default:
                    break;

            }
        }
    };

} // class close




