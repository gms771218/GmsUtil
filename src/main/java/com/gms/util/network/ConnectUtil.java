package com.gms.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

/**
 * Created by gms on 2017/10/21.
 */

public class ConnectUtil {


    /**
     * TODO 會有不準確問題
     * 是否能連上網際網路
     */
    public static boolean isConnectNetwork() {
        Runtime runtime = Runtime.getRuntime();
        try {
            String ip = "8.8.8.8";
            Process ipProcess = runtime.exec("ping -c 2 " + ip);
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 是否使用Wi-Fi連線
     * @param context
     * @return
     */
    public static boolean isWiFiConnect(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info == null)
            return false;
        return info.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
