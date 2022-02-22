package com.example.bssapp.db.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "sport_items")
public class SportItem {

    @Id(autoincrement = true)
    private Long sportId;

    public Long getSportId(){
        return this.sportId;
    }

    private String sportName;

    private Date createDate;

    private boolean deleted;

    @Generated(hash = 453463241)
    public SportItem(Long sportId, String sportName, Date createDate,
            boolean deleted) {
        this.sportId = sportId;
        this.sportName = sportName;
        this.createDate = createDate;
        this.deleted = deleted;
    }

    @Generated(hash = 1310391987)
    public SportItem() {
    }


    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
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

    public boolean getDeleted() {
        return this.deleted;
    }

    public void setSportId(Long sportId) {
        this.sportId = sportId;
    }
}
