package model;

import javafx.beans.property.SimpleStringProperty;

public class Student {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty  sex;
    private SimpleStringProperty  major;
    private SimpleStringProperty  className;
    private SimpleStringProperty  telephone;
    private SimpleStringProperty  state;
    private SimpleStringProperty  instructor;

    public Student(String id, String name, String sex, String major, String className, String telephone, String state, String instructor) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.sex = new SimpleStringProperty(sex);
        this.major = new SimpleStringProperty(major);
        this.className = new SimpleStringProperty(className);
        this.telephone = new SimpleStringProperty(telephone);
        this.state = new SimpleStringProperty(state);
        this.instructor = new SimpleStringProperty(instructor);
    }

    public Student() {
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id =  new SimpleStringProperty(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name =  new SimpleStringProperty(name);
    }

    public String getSex() {
        return sex.get();
    }

    public void setSex(String sex) {
        this.sex =  new SimpleStringProperty(sex);
    }

    public String getMajor() {
        return major.get();
    }

    public void setMajor(String major) {
        this.major =  new SimpleStringProperty(major);
    }

    public String getClassName() {
        return className.get();
    }

    public void setClassName(String className) {
        this.className =  new SimpleStringProperty(className);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String telephone) {
        this.telephone =  new SimpleStringProperty(telephone);
    }

    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state =  new SimpleStringProperty(state);
    }

    public String getInstructor() {
        return instructor.get();
    }

    public void setInstructor(String instructor) {
        this.instructor = new SimpleStringProperty(instructor);
    }
}
