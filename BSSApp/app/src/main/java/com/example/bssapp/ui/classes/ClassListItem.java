package com.example.bssapp.ui.classes;

import java.io.Serializable;

public class ClassListItem implements Serializable {

    public ClassListItem()
    {

    }

    Long ClassId;
    String SportName;
    Long SportId;
    String SpotName;
    String ClassDate;
    String RegisteredNum;

    public ClassListItem(Long classId, String sportName, Long sportId, String spotName, String classDate, String registeredNum)
    {
        this.ClassId = classId;
        this.SportName = sportName;
        this.SportId = sportId;
        this.SpotName = spotName;
        this.ClassDate = classDate;
        this.RegisteredNum = registeredNum;
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

    public String getClassDate() {
        return ClassDate;
    }

    public void setClassDate(String classDate) {
        ClassDate = classDate;
    }

    public String getRegisteredNum() {
        return RegisteredNum;
    }

    public void setRegisteredNum(String registeredNum) {
        this.RegisteredNum = registeredNum;
    }
}
