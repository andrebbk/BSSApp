package com.example.bssapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.FragmentActivity;

import com.example.bssapp.commons.DaysOfWeekValues;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UtilsClass {

    public String NormalizeString(String content){
        content = Normalizer.normalize(content, Normalizer.Form.NFD);
        content = content.replaceAll("[^\\p{ASCII}]", "");

        return content;
    }

    public static Integer CompareDates(Calendar firstDate, Calendar secondDate)
    {
        // 0 - Past
        // 1 - Current
        // 2 - Future

        int firstYear = firstDate.get(Calendar.YEAR);
        int firstMonth = firstDate.get(Calendar.MONTH);
        int firstDay = firstDate.get(Calendar.DAY_OF_YEAR);

        int secondYear = secondDate.get(Calendar.YEAR);
        int secondMonth = secondDate.get(Calendar.MONTH);
        int secondDay = secondDate.get(Calendar.DAY_OF_YEAR);

        if(firstYear > secondYear){
            return 2;
        }
        else if(firstYear < secondYear){
            return 0;
        }
        else if(firstYear == secondYear){
            if(firstMonth > secondMonth){
                return 2;
            }
            else if(firstMonth < secondMonth){
                return 0;
            }
            else if(firstMonth == secondMonth){
                if(firstDay > secondDay){
                    return 2;
                }
                else if(firstDay < secondDay){
                    return 0;
                }
                else
                    return 1;
            }
        }

        return null;
    }

    public static int GetDayOfWeekValue(DaysOfWeekValues dayOfWeek){
        switch(dayOfWeek){
            case SUNDAY:
                return 1;
            case MONDAY:
                return 2;
            case TUESDAY:
                return 3;
            case WEDNESDAY:
                return 4;
            case THURSDAY:
                return 5;
            case FRIDAY:
                return 6;
            case SATURDAY:
                return 7;
            default:
                return 0;
        }
    }

    @SuppressLint("NewApi")
    public static void WriteToBackUp(ArrayList<String> studentsToBk, FragmentActivity activity){

        //ANDROID 10
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.Q) {
            SaveToAndroid10(studentsToBk, activity);
            return;
        }

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
        String fileName = path + "/bss_backup.txt";

        File backUpFile  = new File(String.valueOf(fileName));

        if (!backUpFile.exists()) {
            backUpFile.getParentFile().mkdir();
            try {
                backUpFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(studentsToBk != null && !studentsToBk.isEmpty()) {

            try {
                String lineSeparator = System.getProperty("line.separator");
                FileOutputStream overWrite = new FileOutputStream(fileName, false);

                for (String student : studentsToBk) {
                    overWrite.write(student.getBytes());
                    overWrite.write(lineSeparator.getBytes());
                }

                overWrite.flush();
                overWrite.close();

            } catch (IOException e) {
                e.toString();
            }
        }
    }

    public static void LoadStudentsBackupData(){
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
        String filePath = path + "/bss_backup.txt";

        Charset charset = StandardCharsets.UTF_8;

        try (@SuppressLint({"NewApi", "LocalSuppress"}) BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath), charset)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static void SaveToAndroid10(ArrayList<String> studentsToBk, FragmentActivity activity){
        String data = "";

        if(studentsToBk != null && !studentsToBk.isEmpty()) {

            try {
                for (String student : studentsToBk) {
                    data += student + System.getProperty("line.separator");
                }
            } catch (Exception e) {
                e.toString();
            }
        }

        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "bss_backup.txt");
        values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
        values.put(MediaStore.MediaColumns.IS_PENDING, 1);

        Uri mediaUri = null;

        mediaUri = contentResolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                values);

        //deleteDocumentUri(activity, mediaUri);

        try (OutputStream out = contentResolver.openOutputStream(mediaUri)){

            // Write your data here
            out.write(data.getBytes(StandardCharsets.UTF_8));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        values = new ContentValues();
        values.put(MediaStore.MediaColumns.IS_PENDING, 0);
    }

    private static void deleteDocumentUri(FragmentActivity activity, Uri mediaUri){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && activity != null) {

                Uri documentUri = MediaStore.getDocumentUri(activity, mediaUri);
                if (documentUri != null) {

                    DocumentFile documentFile = DocumentFile.fromSingleUri(activity, documentUri);

                    if (documentFile != null) {
                        if (documentFile.delete()) {
                            Log.i(TAG, "deleteDocumentUri Delete successful");
                        } else {
                            Log.i(TAG, "deleteDocumentUri Delete unsuccessful");
                        }
                    }
                }
            }
        }catch(Exception e){
            Log.e(TAG,"deleteDocumentUri error: " + e);
        }
    }

}
