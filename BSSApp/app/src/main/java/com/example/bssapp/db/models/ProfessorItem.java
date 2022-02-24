package com.example.bssapp.db.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "professor_items")
public class ProfessorItem {

    @Id(autoincrement = true)
    private Long professorId;

    public Long getProfessorId(){
        return this.professorId;
    }

    private String firstName;

    private String lastName;

    private Date createDate;

    private boolean deleted;

    @Generated(hash = 1136881897)
    public ProfessorItem(Long professorId, String firstName, String lastName,
            Date createDate, boolean deleted) {
        this.professorId = professorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createDate = createDate;
        this.deleted = deleted;
    }

    @Generated(hash = 628833183)
    public ProfessorItem() {
    }

    public ProfessorItem(String firstName, String lastName, Date createDate, boolean deleted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createDate = createDate;
        this.deleted = deleted;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }
}
