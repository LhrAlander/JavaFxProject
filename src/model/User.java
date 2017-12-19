package model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Alander on 2017/12/14.
 */
public class User {
    private SimpleStringProperty userId;
    private SimpleStringProperty userName;
    private SimpleStringProperty userSex;
    private SimpleStringProperty telephone;
    private SimpleStringProperty userIdentity;
    private SimpleStringProperty userPassword;
    private SimpleStringProperty state;

    public User(String userId, String userName, String userSex, String userPhone, String telephone, String userIdentity, String userPassword) {
        this.userId = new SimpleStringProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.userSex = new SimpleStringProperty(userSex);
        this.telephone = new SimpleStringProperty(telephone);
        this.userIdentity = new SimpleStringProperty(userIdentity);
        this.userPassword = new SimpleStringProperty(userPassword);
    }

    public User() {
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

    public String getState() {
        return state.get();
    }


    public void setState(String state) {
        this.state = new SimpleStringProperty(state);
    }

    @Override
    public String toString() {
        return "userId: " + getUserId() + ", userName: " + getUserName() + ", userIdentity: " + getUserIdentity() ;
    }
}
