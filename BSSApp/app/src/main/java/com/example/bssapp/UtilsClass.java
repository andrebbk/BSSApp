package com.example.bssapp;

import android.os.Environment;

import com.example.bssapp.commons.DaysOfWeekValues;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;

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

    public static void WriteToBackUp(ArrayList<String> studentsToBk){
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

}
