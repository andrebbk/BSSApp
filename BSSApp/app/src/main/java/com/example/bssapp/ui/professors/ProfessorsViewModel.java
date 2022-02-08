package com.example.bssapp.ui.professors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfessorsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProfessorsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is professors fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

