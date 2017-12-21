package model;

import javafx.beans.property.SimpleStringProperty;

public class Student {
    private SimpleStringProperty userId;
    private SimpleStringProperty userName;
    private SimpleStringProperty userSex;
    private SimpleStringProperty telephone;
    private SimpleStringProperty userIdentity;
    private SimpleStringProperty userPassword;
    private SimpleStringProperty instructor;
    private SimpleStringProperty instructorName;
    private SimpleStringProperty state;



    public Student(String userId, String userName, String userSex, String telephone, String userIdentity, String userPassword, String instructor, String state) {
        this.userId = new SimpleStringProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.userSex = new SimpleStringProperty(userSex);
        this.telephone = new SimpleStringProperty(telephone);
        this.userIdentity = new SimpleStringProperty(userIdentity);
        this.userPassword = new SimpleStringProperty(userPassword);
        this.instructor = new SimpleStringProperty(instructor);
        this.state = new SimpleStringProperty(state);
    }

    public Student(User user, String instructor, String state) {
        this(user.getUserId(), user.getUserName(), user.getUserSex(), user.getTelephone(), user.getUserIdentity(), user.getUserPassword(), instructor, state);
    }

    public Student(String userId, String userName, String instructor, String state) {
        this.userId = new SimpleStringProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.instructor = new SimpleStringProperty(instructor);
        this.state = new SimpleStringProperty(state);
    }

    public Student() {
    }

    public String getUserId() {
        return userId.get();
    }

    public void setUserId(String userId) {
        this.userId = new SimpleStringProperty(userId);
    }

    public String getUserName() {
        return userName.get();
    }


    public void setUserName(String userName) {
        this.userName = new SimpleStringProperty(userName);
    }

    public String getInstructorName() {
        if (instructorName == null) {
            return "";
        }
        return instructorName.get();
    }


    public void setInstructorName(String userName) {
        this.instructorName = new SimpleStringProperty(userName);
    }

    public String getUserSex() {
        return userSex.get();
    }


    public void setUserSex(String userSex) {
        this.userSex = new SimpleStringProperty(userSex);
    }

    public String getTelephone() {
        return telephone.get();
    }


    public void setTelephone(String telephone) {
        this.telephone = new SimpleStringProperty(telephone);
    }

    public String getUserIdentity() {
        return userIdentity.get();
    }


    public void setUserIdentity(String userIdentity) {
        this.userIdentity = new SimpleStringProperty(userIdentity);
    }

    public String getUserPassword() {
        return userPassword.get();
    }


    public void setUserPassword(String userPassword) {
        this.userPassword = new SimpleStringProperty(userPassword);
    }

    public String getInstructor() {
        return instructor.get();
    }


    public void setInstructor(String instructor) {
        this.instructor = new SimpleStringProperty(instructor);
    }

    public String getState() {
        return state.get();
    }


    public void setState(String state) {
        this.state = new SimpleStringProperty(state);
    }
}
