package com.example.bssapp.ui.classes;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class DropdownViewModel implements Serializable {

    private Long id;
    private String text;

    public DropdownViewModel(Long _id, String _text){
        this.id = _id;
        this.text = _text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
