package com.example.bssapp.db.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "spot_items")
public class SpotItem {

    @Id(autoincrement = true)
    private Long spotId;

    public Long getSpotId(){
        return this.spotId;
    }

    private String spotName;

    private Date createDate;

    private boolean deleted;

    @Generated(hash = 1045630016)
    public SpotItem(Long spotId, String spotName, Date createDate,
            boolean deleted) {
        this.spotId = spotId;
        this.spotName = spotName;
        this.createDate = createDate;
        this.deleted = deleted;
    }

    @Generated(hash = 18064977)
    public SpotItem() {
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
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

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public boolean getDeleted() {
        return this.deleted;
    }
}
