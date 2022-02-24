package com.example.bssapp.db.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "classStudent_items")
public class ClassStudentItem {

    @Id(autoincrement = true)
    private Long classStudentId;

    public Long getClassStudentId(){ return this.classStudentId; }

    private Long classId;

    private Long studentId;

    private Date createDate;

    @Generated(hash = 1549258127)
    public ClassStudentItem(Long classStudentId, Long classId, Long studentId,
            Date createDate) {
        this.classStudentId = classStudentId;
        this.classId = classId;
        this.studentId = studentId;
        this.createDate = createDate;
    }

    @Generated(hash = 926245656)
    public ClassStudentItem() {
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setClassStudentId(Long classStudentId) {
        this.classStudentId = classStudentId;
    }
}
