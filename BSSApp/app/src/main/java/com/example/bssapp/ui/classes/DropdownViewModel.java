package com.example.bssapp.ui.classes;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class DropdownViewModel implements Serializable {

    private int id;
    private String text;

    public DropdownViewModel(int _id, String _text){
        this.id = _id;
        this.text = _text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return text;
    }

}
