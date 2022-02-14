package com.example.bssapp.ui.professors;

import java.io.Serializable;

public class ProfessorListItem implements Serializable {

    Long ProfessorId;
    String ProfessorName;
    int ProfessorImage;

    public ProfessorListItem()
    {

    }

    public ProfessorListItem(Long id, String name, int image)
    {
        this.ProfessorId = id;
        this.ProfessorName = name;
        this.ProfessorImage = image;
    }

    public Long getProfessorId() {
        return ProfessorId;
    }

    public void setProfessorId(Long professorId) {
        ProfessorId = professorId;
    }

    public String getProfessorName() {
        return ProfessorName;
    }

    public void setProfessorName(String studentName) {
        ProfessorName = studentName;
    }

    public int getProfessorImage() {
        return ProfessorImage;
    }

    public void setProfessorImage(int studentImage) {
        ProfessorImage = studentImage;
    }
}
