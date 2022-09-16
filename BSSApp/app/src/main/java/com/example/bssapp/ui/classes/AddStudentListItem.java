package com.example.bssapp.ui.classes;

import java.io.Serializable;

public class AddStudentListItem implements Serializable {

    Long StudentId;
    String StudentName;
    int StudentImage;
    boolean IsRegistred;

    public AddStudentListItem()
    {

    }

    public AddStudentListItem(Long id, String name, int image, boolean isRegistred)
    {
        this.StudentId = id;
        this.StudentName = name;
        this.StudentImage = image;
        this.IsRegistred = isRegistred;
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

    public boolean getIsRegistred() {
        return IsRegistred;
    }

    public void setIsRegistred(boolean isRegistred) {
        IsRegistred = isRegistred;
    }
}
