package com.hgsft.justrun;

import android.app.Application;

import com.hgstf.utils.SavePrefs;

/**
 * Created by Ilya on 19.12.2017.
 */

public class JustRunApp extends Application {
    @Override
    public void onCreate() {
        SavePrefs.init(this);
        super.onCreate();
    }
}
