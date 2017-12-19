package com.gms.util;

import android.content.Context;
import android.util.DisplayMetrics;

import java.text.DecimalFormat;

/**
 * Created by gms on 2017/10/20.
 */

public class ConvertUtil {

    /**
     * Dp To Px
     * @param context
     * @param dp
     * @return
     */
    public static float convertDpToPx(Context context, float dp) {
        return dp * getDensity(context);
    }

    /**
     * Px To Dp
     * @param context
     * @param px
     * @return
     */
    public static float convertPxToDp(Context context, float px) {
        return px / getDensity(context);
    }

    private static float getDensity(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }


    static int GB = 1024 * 1024 * 1024;     //GB計算常量
    static int MB = 1024 * 1024;            //MB計算常量
    static int KB = 1024;                   //KB計算常量

    /**
     * Byte Size
     * @param kSize
     * @return
     */
    public static String byteConversionGBMBKB(Long kSize) {
        DecimalFormat df = new DecimalFormat("######0.0");
        if (kSize / GB >= 1)
            return df.format(kSize / (float) GB).toString() + "G";
        else if (kSize / MB >= 1)//如果当前Byte的值大于等于1MB
            return df.format(kSize / (float) MB).toString() + "M";
        else if (kSize / KB >= 1)//如果当前Byte的值大于等于1KB
            return df.format(kSize / (float) KB).toString() + "K";
        else
            return kSize.toString() + "B";
    }

    /**
     * Byte Size
     * @param kSize
     * @return
     */
    public static String byteConversionGBMBKB(Integer kSize) {
        DecimalFormat df = new DecimalFormat("######0.0");
        if (kSize / GB >= 1)
            return df.format(kSize / (float) GB).toString() + "G";
        else if (kSize / MB >= 1)//如果当前Byte的值大于等于1MB
            return df.format(kSize / (float) MB).toString() + "M";
        else if (kSize / KB >= 1)//如果当前Byte的值大于等于1KB
            return df.format(kSize / (float) KB).toString() + "K";
        else
            return kSize.toString() + "B";
    }

}
