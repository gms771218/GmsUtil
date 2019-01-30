package com.gms.util.interfaces;

/**
 * Created by gms on 2019.01.25
 * 監控Activity，判斷keyboard顯示or隱藏
 *
 * refer : https://stackoverflow.com/questions/4312319/how-to-capture-the-virtual-keyboard-show-hide-event-in-android
 */
public interface IOnKeyboardVisibilityListener {
    void onVisibilityChanged(boolean visible) ;
}
