package com.example.bssapp;

import java.text.Normalizer;

public class UtilsClass {

    public String NormalizeString(String content){
        content = Normalizer.normalize(content, Normalizer.Form.NFD);
        content = content.replaceAll("[^\\p{ASCII}]", "");

        return content;
    }

}
