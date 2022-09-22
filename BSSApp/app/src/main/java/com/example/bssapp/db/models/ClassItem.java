package com.example.bssapp.db.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Calendar;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "class_items")
public class ClassItem {

    @Id(autoincrement = true)
    private Long classId;

    public Long getClassId(){ return this.classId; }

    private Long sportId;

    private Date classDateTime;

    private int classDayOfWeek;

    private Long spotId;

    public String observations;

    private Date createDate;

    private boolean deleted;

    @Generated(hash = 1808825472)
    public ClassItem(Long classId, Long sportId, Date classDateTime,
            int classDayOfWeek, Long spotId, String observations, Date createDate,
            boolean deleted) {
        this.classId = classId;
        this.sportId = sportId;
        this.classDateTime = classDateTime;
        this.classDayOfWeek = classDayOfWeek;
        this.spotId = spotId;
        this.observations = observations;
        this.createDate = createDate;
        this.deleted = deleted;
    }

    @Generated(hash = 206792637)
    public ClassItem() {
    }

    public Long getSportId() {
        return sportId;
    }

    public void setSportId(Long sportId) {
        this.sportId = sportId;
    }

    public Date getClassDateTime() {
        return classDateTime;
    }

    public void setClassDateTime(Date classDateTime) {
        this.classDateTime = classDateTime;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public int getClassDayOfWeek() {
        return this.classDayOfWeek;
    }

    public void setClassDayOfWeek(int classDayOfWeek) {
        this.classDayOfWeek = classDayOfWeek;
    }
}
