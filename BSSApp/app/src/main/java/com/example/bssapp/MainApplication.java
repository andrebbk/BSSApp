package com.example.bssapp;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

public class MainApplication extends Application {

    private com.example.bssapp.DaoSession daoSession;

    @Override
    public void onCreate(){
        super.onCreate();

        //Only run when db schema is changed/updated
        //clearDatabase(this);

        //regular SQLite Database
        com.example.bssapp.DaoMaster.DevOpenHelper helper = new com.example.bssapp.DaoMaster.DevOpenHelper(this, "bbs", null);
        Database db = helper.getWritableDb();

        daoSession = new com.example.bssapp.DaoMaster(db).newSession();
    }

    public com.example.bssapp.DaoSession getDaoSession(){
        return daoSession;
    }

    public static void clearDatabase(Context context) {
        com.example.bssapp.DaoMaster.DevOpenHelper devOpenHelper = new com.example.bssapp.DaoMaster.DevOpenHelper(
                context, "bbs", null);
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        devOpenHelper.onUpgrade(db,0,0);
    }
}
