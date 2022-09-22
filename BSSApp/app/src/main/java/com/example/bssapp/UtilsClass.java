package com.example.bssapp;

import java.text.Normalizer;
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

}
