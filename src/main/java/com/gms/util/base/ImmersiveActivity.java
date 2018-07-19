package com.gms.util.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.gms.util.device.Screen;

/**
 * Created by gms on 2017/10/20.
 * <p>
 * 沉浸式Activity
 * refer : https://developer.android.com/training/system-ui/immersive.html
 */

public class ImmersiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Screen.Initialize(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Screen.Destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUi();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus)
        hideSystemUi();
//        else {
//            View decorView = getWindow().getDecorView();
//            decorView.setOnSystemUiVisibilityChangeListener(null);
//        }
    }

    public void hideSystemUi() {
        final View decorView = setSystemUiVisibilityMode();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                setSystemUiVisibilityMode(); // Needed to avoid exiting immersive_sticky when keyboard is displayed
            }
        });
    }

    protected View setSystemUiVisibilityMode() {

        View decorView = getWindow().getDecorView();

//        int options;
//        options =
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION   // hide nav bar
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN        // hide status bar
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//
////        decorView.setSystemUiVisibility(options);
//
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        return decorView;
    }

}
