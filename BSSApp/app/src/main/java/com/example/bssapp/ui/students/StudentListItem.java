package com.example.bssapp.ui.students;

import java.io.Serializable;

public class StudentListItem implements Serializable {

    Long StudentId;
    String StudentName;
    int StudentImage;
    long StudentClassesCount;

    public StudentListItem()
    {

    }

    public StudentListItem(Long id, String name, int image, long studentClassesCount)
    {
        this.StudentId = id;
        this.StudentName = name;
        this.StudentImage = image;
        this.StudentClassesCount = studentClassesCount;
    }

    public Long getStudentId() {
        return StudentId;
    }

    public void setStudentId(Long studentId) {
        StudentId = studentId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public int getStudentImage() {
        return StudentImage;
    }

    public void setStudentImage(int studentImage) {
        StudentImage = studentImage;
    }

    public long getStudentClassesCount() {
        return StudentClassesCount;
    }

    public void setStudentClassesCount(long studentClassesCount) { StudentClassesCount = studentClassesCount; }
}
