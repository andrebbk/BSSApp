package com.example.bssapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart;
    private TextView textWeekClasses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textWeekClasses = findViewById(R.id.textViewWeekClasses);

        buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(view -> {
            try {
                Intent k = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(k);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });

        AnnounceWeekClassesMsg();
    }

    @SuppressLint("SetTextI18n")
    private void AnnounceWeekClassesMsg()
    {
        Calendar startOfWeek = Calendar.getInstance();
        startOfWeek.setTime(getDayOfWeek(Calendar.MONDAY));
        startOfWeek.set(Calendar.HOUR_OF_DAY, 0);
        startOfWeek.set(Calendar.MINUTE, 0);

        Calendar endOfWeek = Calendar.getInstance();
        endOfWeek.setTime(getDayOfWeek(Calendar.SUNDAY));
        endOfWeek.set(Calendar.HOUR_OF_DAY, 23);
        endOfWeek.set(Calendar.MINUTE, 59);

        //Db
        com.example.bssapp.DaoSession daoSession = ((MainApplication) getApplication()).getDaoSession();

        long nClasses = daoSession.getClassItemDao().queryBuilder()
                .where(com.example.bssapp.ClassItemDao.Properties.Deleted.notEq(1),
                        com.example.bssapp.ClassItemDao.Properties.ClassDateTime.ge(startOfWeek.getTime()),
                        com.example.bssapp.ClassItemDao.Properties.ClassDateTime.le(endOfWeek.getTime()))
                .count();

        if(nClasses > 0)
        {
            if(nClasses > 1)
                textWeekClasses.setText("Tens " + nClasses + " aulas marcadas esta semana");
            else
                textWeekClasses.setText("Tens 1 aula marcada esta semana");
        }
        else { textWeekClasses.setText("Não tens aulas marcadas esta semana"); }
    }

    private static Date getDayOfWeek(int calendarField) {
        Date strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            int week = c.get(Calendar.DAY_OF_WEEK);
            if (week == calendarField) {
                strDate = c.getTime();
            } else {
                int offectDay = calendarField - week;
                if (calendarField == Calendar.SUNDAY) {
                    offectDay = 7 - Math.abs(offectDay);
                }
                c.add(Calendar.DATE, offectDay);
                strDate = c.getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }
}