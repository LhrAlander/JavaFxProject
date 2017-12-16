package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Alander on 2017/12/15.
 */
public class Teacher {
    private SimpleStringProperty userId;
    private SimpleStringProperty name;
    private SimpleStringProperty teacherTitle;
    private SimpleStringProperty teacherMajor;
    private SimpleIntegerProperty studentNumber;
    private SimpleStringProperty telephone;
    private SimpleStringProperty canBeSelected;

    public Teacher (String userId, String name, String teacherTitle, String teacherMajor, int studentNumber) {
        this.userId = new SimpleStringProperty(userId);
        this.name = new SimpleStringProperty(name);
        this.teacherTitle = new SimpleStringProperty(teacherTitle);
        this.teacherMajor = new SimpleStringProperty(teacherMajor);
        this.studentNumber = new SimpleIntegerProperty(studentNumber);
    }

    public Teacher() {
    }

    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String telephone) {
        this.telephone = new SimpleStringProperty(telephone);
    }

    public String getCanBeSelected() {
        return canBeSelected.get();
    }

    public void setCanBeSelected(String canBeSelected) {
        this.canBeSelected = new SimpleStringProperty(canBeSelected);
    }


    public String getName () {
        return name.get();
    }

    public void setName (String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getUserId () {
        return userId.get();
    }

    public void setUserId (String userId) {
        this.userId = new SimpleStringProperty(userId);
    }

    public String getTeacherTitle () {
        return teacherTitle.get();
    }

    public void setTeacherTitle (String teacherTitle) {
        this.teacherTitle = new SimpleStringProperty(teacherTitle);
    }

    public String getTeacherMajor () {
        return teacherMajor.get();
    }

    public void setTeacherMajor (String teacherMajor) {
        this.teacherMajor = new SimpleStringProperty(teacherMajor);
    }

    public int getStudentNumber () {
        return studentNumber.get();
    }

    public void setStudentNumber (int studnetNumber) {
        this.studentNumber = new SimpleIntegerProperty(studnetNumber);
    }
}
