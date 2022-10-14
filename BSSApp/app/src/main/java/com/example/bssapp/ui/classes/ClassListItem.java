package com.example.bssapp.ui.classes;

import java.io.Serializable;
import java.util.Calendar;

public class ClassListItem implements Serializable {

    public ClassListItem()
    {

    }

    Long ClassId;
    String SportName;
    Long SportId;
    String SpotName;
    Long SpotId;
    String ClassDate;
    String CompleteClassDate;
    String RegisteredNum;
    boolean isEmptyRow;
    Calendar ClassDateValue;

    public ClassListItem(Long classId, String sportName, Long sportId, String spotName, Long spotId, String classDate, Calendar classDateValue, String registeredNum, String completeClassDate)
    {
        this.ClassId = classId;
        this.SportName = sportName;
        this.SportId = sportId;
        this.SpotName = spotName;
        this.SpotId = spotId;
        this.ClassDate = classDate;
        this.RegisteredNum = registeredNum;
        this.CompleteClassDate = completeClassDate;
        this.isEmptyRow = false;
        this.ClassDateValue = classDateValue;
    }

    public ClassListItem(boolean isEmpty)
    {
        this.ClassId = Long.valueOf(0);
        this.isEmptyRow = isEmpty;
    }

    public Long getClassId() {
        return ClassId;
    }

    public void setClassId(Long classId) {
        ClassId = classId;
    }

    public String getSportName() {
        return SportName;
    }

    public void setSportName(String sportName) {
        SportName = sportName;
    }

    public Long getSportId() {
        return SportId;
    }

    public void setSportId(Long sportId) {
        SportId = sportId;
    }

    public String getSpotName() {
        return SpotName;
    }

    public void setSpotName(String spotName) {
        SpotName = spotName;
    }

    public Long getSpotId() {
        return SpotId;
    }

    public void setSpotId(Long spotId) {
        SpotId = spotId;
    }

    public String getClassDate() {
        return ClassDate;
    }

    public void setClassDate(String classDate) {
        ClassDate = classDate;
    }

    public Calendar getClassDateValue() {
        return ClassDateValue;
    }

    public void setClassDateValue(Calendar classDateValue) { ClassDateValue = classDateValue; }

    public String getRegisteredNum() {
        return RegisteredNum;
    }

    public void setRegisteredNum(String registeredNum) {
        this.RegisteredNum = registeredNum;
    }

    public String getCompleteClassDate() {
        return CompleteClassDate;
    }

    public void setCompleteClassDate(String completeClassDate) { CompleteClassDate = completeClassDate; }

    public boolean isDummyRow() {
        return isEmptyRow;
    }

    public void setDummyRow(boolean dummyRow) {
        isEmptyRow = dummyRow;
    }
}
