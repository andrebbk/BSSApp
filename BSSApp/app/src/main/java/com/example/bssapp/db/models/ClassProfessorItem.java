package com.example.bssapp.db.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "classProfessor_items")
public class ClassProfessorItem {

    @Id(autoincrement = true)
    private Long classProfessorId;

    public Long getClassProfessorId(){ return this.classProfessorId; }

    private Long classId;

    private Long professorId;

    private Date createDate;

    @Generated(hash = 349765389)
    public ClassProfessorItem(Long classProfessorId, Long classId, Long professorId,
            Date createDate) {
        this.classProfessorId = classProfessorId;
        this.classId = classId;
        this.professorId = professorId;
        this.createDate = createDate;
    }

    @Generated(hash = 1398358731)
    public ClassProfessorItem() {
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setClassProfessorId(Long classProfessorId) {
        this.classProfessorId = classProfessorId;
    }
}
