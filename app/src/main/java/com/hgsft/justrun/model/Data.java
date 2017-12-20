package com.hgsft.justrun.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hgstf.utils.SavePrefs;

/**
 * Created by Ilya on 18.12.2017.
 */

public class Data {

    private static final String SERIALIZE_KEY = "SAVE_CONFIG";
    public static void save() {
        if (currentData == null) {
            if (SavePrefs.containsSave(SERIALIZE_KEY)) {
                return;
            }
            //save anyway
            currentData = getDefault();
        }
        Gson gson = new GsonBuilder().create();
        String serialized = gson.toJson(currentData);
        SavePrefs.save(SERIALIZE_KEY, serialized);
    }

    public static Data read() {
        String serialized = SavePrefs.read(SERIALIZE_KEY);
        if (serialized == null) {
            return null;
        } else {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(serialized, Data.class);
        }
    }

    private static Data getDefault() {
        Data d = new Data();
        d.settings = Settings.getDefault();
        d.setCurrentVersion(Upgrade.getCurrentVersion());
        return d;
        /*return new Data() {
            {
                this.currentVersion = Upgrade.getCurrentVersion();
                this.settings = Settings.getDefault();
            }
        };*/
    }

    private static Data currentData = null;
    public static Data getCurrent() {
        if (currentData == null) {
            currentData = read();
        }
        if (currentData == null) {
            currentData = getDefault();
        }
        return currentData;
    }

    private int currentVersion;
    public int getCurrentVersion() { return this.currentVersion; }
    public void setCurrentVersion(int version) { this.currentVersion = version; }

    private Settings settings;
    public Settings getSettings() { return this.settings; }

}
