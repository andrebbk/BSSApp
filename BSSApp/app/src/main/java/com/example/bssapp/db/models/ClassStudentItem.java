package com.example.bssapp.db.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import com.example.bssapp.DaoSession;
import com.example.bssapp.StudentItemDao;
import com.example.bssapp.ClassItemDao;
import com.example.bssapp.ClassStudentItemDao;

@Entity(nameInDb = "classStudent_items")
public class ClassStudentItem {

    @Id(autoincrement = true)
    private Long classStudentId;

    public Long getClassStudentId(){ return this.classStudentId; }

    private Long classId;

    private Long studentId;

    private Date createDate;

    @ToOne(joinProperty = "classId")
    private ClassItem classs;

    @ToOne(joinProperty = "studentId")
    private StudentItem student;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 345541049)
    private transient ClassStudentItemDao myDao;

    @Generated(hash = 1934405185)
    private transient Long classs__resolvedKey;

    @Generated(hash = 79695740)
    private transient Long student__resolvedKey;

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

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 333269671)
    public ClassItem getClasss() {
        Long __key = this.classId;
        if (classs__resolvedKey == null || !classs__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ClassItemDao targetDao = daoSession.getClassItemDao();
            ClassItem classsNew = targetDao.load(__key);
            synchronized (this) {
                classs = classsNew;
                classs__resolvedKey = __key;
            }
        }
        return classs;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1195656119)
    public void setClasss(ClassItem classs) {
        synchronized (this) {
            this.classs = classs;
            classId = classs == null ? null : classs.getClassId();
            classs__resolvedKey = classId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1603772387)
    public StudentItem getStudent() {
        Long __key = this.studentId;
        if (student__resolvedKey == null || !student__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StudentItemDao targetDao = daoSession.getStudentItemDao();
            StudentItem studentNew = targetDao.load(__key);
            synchronized (this) {
                student = studentNew;
                student__resolvedKey = __key;
            }
        }
        return student;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 808073535)
    public void setStudent(StudentItem student) {
        synchronized (this) {
            this.student = student;
            studentId = student == null ? null : student.getStudentId();
            student__resolvedKey = studentId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 915458170)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getClassStudentItemDao() : null;
    }
    
}
