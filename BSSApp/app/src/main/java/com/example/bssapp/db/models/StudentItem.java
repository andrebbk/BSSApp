package com.example.bssapp.db.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "student_items")
public class StudentItem {

    @Id(autoincrement = true)
    private Long studentId;

    public Long getStudentId(){
        return this.studentId;
    }

    private String firstName;

    private String lastName;

    private boolean isAdult;

    private Date createDate;

    private boolean deleted;

    @Generated(hash = 2133446958)
    public StudentItem(Long studentId, String firstName, String lastName,
            boolean isAdult, Date createDate, boolean deleted) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdult = isAdult;
        this.createDate = createDate;
        this.deleted = deleted;
    }

    @Generated(hash = 383807586)
    public StudentItem() {
    }

    public StudentItem(String firstName, String lastName, boolean isAdult, Date createDate, boolean deleted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdult = isAdult;
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

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
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

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public boolean getIsAdult() {
        return this.isAdult;
    }

    public void setIsAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }

    public boolean getDeleted() {
        return this.deleted;
    }
}
