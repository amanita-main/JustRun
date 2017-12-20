package com.hgsft.justrun.model;

/**
 * Created by Ilya on 18.12.2017.
 */

public class Upgrade {
    private static int currentVersion = 1;
    public static int getCurrentVersion() { return currentVersion; }
    public static Data upgradeData(Data data) {
        assert(data == null);
        int dataVersion = data.getCurrentVersion();
        if (dataVersion < currentVersion) {
            switch (dataVersion) {
                case 1:
                    //upgrade to 2
                    ++dataVersion;
                    data.setCurrentVersion(dataVersion);
                    break;
            }
            return upgradeData(data);
        }
        return data;
    }
}
