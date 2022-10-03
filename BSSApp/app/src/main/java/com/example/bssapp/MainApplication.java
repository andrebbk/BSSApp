package com.example.bssapp;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.bssapp.db.models.ProfessorItem;
import com.example.bssapp.db.models.SportItem;
import com.example.bssapp.db.models.SpotItem;
import com.example.bssapp.db.models.StudentItem;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.gradle.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

        InitialSeedDB();
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

    private void InitialSeedDB()
    {
        Date currentDate = Calendar.getInstance().getTime();

        //Sports
        //Modalidades
        com.example.bssapp.SportItemDao sportItemDao = daoSession.getSportItemDao();
        if(sportItemDao.queryBuilder()
                .where(com.example.bssapp.SportItemDao.Properties.Deleted.notEq(1)).count() < 1)
        {
            ArrayList<String> sportsList = new ArrayList<>(Arrays.asList("Surf", "SUPaddle", "Yoga", "Canoagem"));

            for (String spr : sportsList)
            {
                SportItem newS = new SportItem();
                newS.setSportName(spr);
                newS.setCreateDate(currentDate);
                newS.setDeleted(false);

                sportItemDao.save(newS);
            }
        }

        //Spots
        //Locais
        com.example.bssapp.SpotItemDao spotItemDao = daoSession.getSpotItemDao();
        if(spotItemDao.queryBuilder()
                .where(com.example.bssapp.SpotItemDao.Properties.Deleted.notEq(1)).count() < 1)
        {
            ArrayList<String> spotsList = new ArrayList<>(
                    Arrays.asList("Barrinha", "Praia Velha", "Esmoriz", "Cortegaça", "Espinho", "Cangas", "Douro", "Paramos", "Furadouro"));

            for (String sp : spotsList)
            {
                SpotItem newSp = new SpotItem();
                newSp.setSpotName(sp);
                newSp.setCreateDate(currentDate);
                newSp.setDeleted(false);

                spotItemDao.save(newSp);
            }
        }

        //Professors
        //Instrutores
        com.example.bssapp.ProfessorItemDao professorItemDao = daoSession.getProfessorItemDao();
        if(professorItemDao.queryBuilder()
                .where(com.example.bssapp.ProfessorItemDao.Properties.Deleted.notEq(1)).count() < 1)
        {
            professorItemDao.save(new ProfessorItem("Mário", "Saxe", currentDate, false));
            professorItemDao.save(new ProfessorItem("Inês", "Costa", currentDate, false));
            professorItemDao.save(new ProfessorItem("Miguel", "Oliveira", currentDate, false));
            professorItemDao.save(new ProfessorItem("Pedro", "Silva", currentDate, false));
        }

        //Students
        //Alunos
        com.example.bssapp.StudentItemDao studentItemDao = daoSession.getStudentItemDao();
        if(studentItemDao.queryBuilder()
                .where(com.example.bssapp.StudentItemDao.Properties.Deleted.notEq(1)).count() < 1)
        {
            studentItemDao.save(new StudentItem("André", "Silva", true, currentDate, false));
            studentItemDao.save(new StudentItem("Leonor", "Mendes", false, currentDate, false));
            studentItemDao.save(new StudentItem("Pedro", "Costa", true, currentDate, false));
            studentItemDao.save(new StudentItem("Jacinto", "Pereira", true, currentDate, false));
            studentItemDao.save(new StudentItem("Ernesto", "Carvalho", true, currentDate, false));
            studentItemDao.save(new StudentItem("Luís", "Fonseca", true, currentDate, false));
            studentItemDao.save(new StudentItem("Tiago", "Nunes", false, currentDate, false));
            studentItemDao.save(new StudentItem("Ana", "Moreira", false, currentDate, false));
        }
    }
}
