package com.gms.util.device;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by gms on 2017/10/20.
 */

public class Screen {

    String TAG = "Screen";

    private static Screen instance;

    /**
     * 狀態列(StatusBar)高度 Px
     */
    private int statusBarHeight = 0;

    /**
     * NavigationBar 高度 Px
     */
    private int navigationBarHeight = 0;

    /**
     * 螢幕寬度
     */
    public int mWidth;

    /**
     * 螢幕高度
     */
    public int mHeight;

    /**
     * 裝置密度
     */
    public float mDensity ;

    /**
     * 裝置Dpi
     */
    public int mDensityDpi ;



    public static Screen getInstance() {
        if (instance == null)
            throw new RuntimeException("call Screen.init()");
        return instance;
    }

    private Screen(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int statusBarID = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusBarHeight = activity.getResources().getDimensionPixelSize(statusBarID);
        int navigationBarID = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (navigationBarExist(activity))
            navigationBarHeight = navigationBarID > 0 ? activity.getResources().getDimensionPixelSize(navigationBarID) : 0;
        this.mWidth = size.x;
        this.mHeight = size.y;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        mDensity = displayMetrics.density ;
        mDensityDpi = displayMetrics.densityDpi ;

    }

    /**
     * 判斷是否有NavigateBar
     *
     * @param context
     * @return
     */
    public boolean navigationBarExist(Context context) {
        int id = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 && context.getResources().getBoolean(id);
    }

    public static void init(Activity activity) {
        if (instance == null) {
            instance = new Screen(activity);
        }
    }


    // ==================== Object Method ====================

    /**
     * 狀態列(StatusBar)高度 Px
     */
    public int getStatusBarHeight() {
        return statusBarHeight;
    }

    /**
     * NavigationBar 高度 Px
     */
    public int getNavigationBarHeight() {
        return navigationBarHeight;
    }

    /**
     * 裝置密度
     */
    public float getDensity() {
        return mDensity ;
    }

    /**
     * 裝置Dpi
     */
    public float getDensityDpi() {
        return mDensityDpi ;
    }




    @Override
    public String toString() {
        return "Screen{" +
                ", statusBarHeight=" + getStatusBarHeight() +
                ", navigationBarHeight=" + getNavigationBarHeight() +
                ", Screen(" + mWidth + " / " + mHeight + ")" +
                ", Density="+getDensity() +
                ", DensityDpi="+getDensityDpi() +
                "}";
    }
}
