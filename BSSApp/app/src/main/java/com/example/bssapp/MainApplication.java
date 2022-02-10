package com.example.bssapp;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

public class MainApplication extends Application {

    private com.example.bssapp.DaoSession daoSession;

    @Override
    public void onCreate(){
        super.onCreate();

        //regular SQLite Database
        com.example.bssapp.DaoMaster.DevOpenHelper helper = new com.example.bssapp.DaoMaster.DevOpenHelper(this, "bbs", null);
        Database db = helper.getWritableDb();

        daoSession = new com.example.bssapp.DaoMaster(db).newSession();
    }

    public com.example.bssapp.DaoSession getDaoSession(){
        return daoSession;
    }
}
