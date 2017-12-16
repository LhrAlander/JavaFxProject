package dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Student;
import model.Teacher;
import model.User;
import util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDao {



    private Connection conn;
    private  PreparedStatement stateQuery;
    private PreparedStatement teachersql;
    private PreparedStatement changeInstructorUpdate;
    private PreparedStatement cancelInstructorUpdate;
    private PreparedStatement canSelectSql;

    public StudentDao () {
        conn= DBHelper.getConnection();
        try {
            stateQuery = conn.prepareStatement("select * from student where state=?");
            teachersql = conn.prepareStatement("select * from student where instructor = ?");
            changeInstructorUpdate = conn.prepareStatement("UPDATE student set instructor = ?, state = ? where userId = ?");
            cancelInstructorUpdate = conn.prepareStatement("UPDATE student set instructor = 'null', state = '未选' where userId = ?");
            canSelectSql = conn.prepareStatement("select * from student where userId = ?;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Student> findStudentsByState (String state) {
        ObservableList<Student> students = FXCollections.observableArrayList();

        try {
            stateQuery.setString(1, state);
            ResultSet rs = stateQuery.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setUserId(rs.getString("userId"));
                student.setInstructor(rs.getString("instructor"));
                student.setUserName(rs.getString("name"));
                student.setState(rs.getString("state"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public ObservableList<Student> findStudentsByInstructor (String instructor, String name) {
        ObservableList<Student> students = FXCollections.observableArrayList();
        try {
            teachersql.setString(1, instructor);
            ResultSet rs = teachersql.executeQuery();
            while (rs.next()) {
                students.add(getStudentByRs(rs, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    private Student getStudentByRs (ResultSet rs, String name) {
        Student student = new Student();
        try {
            student.setUserId(rs.getString("userId"));
            student.setInstructor(name);
            student.setState(rs.getString("state"));
            student.setUserName(rs.getString("name"));
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public Boolean changeInstructor (String studentId, String teacherId, String state) {
        try {
            changeInstructorUpdate.setString(1, teacherId);
            changeInstructorUpdate.setString(2, state);
            changeInstructorUpdate.setString(3, studentId);
            changeInstructorUpdate.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean canSelectTeacher (User user) {
        try {
            canSelectSql.setString(1, user.getUserId());
            ResultSet rs = canSelectSql.executeQuery();
            rs.next();
            if (rs.getString("state").equals("未选")) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean canCancelTeacher (User user) {
        try {
            canSelectSql.setString(1, user.getUserId());
            ResultSet rs = canSelectSql.executeQuery();
            rs.next();
            if (rs.getString("state").equals("待定")) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean cancelTeacher (User user) {
        try {
            cancelInstructorUpdate.setString(1, user.getUserId());
            cancelInstructorUpdate.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getInstructor (User user) {
        try {
            canSelectSql.setString(1, user.getUserId());
            ResultSet rs = canSelectSql.executeQuery();
            rs.next();
            if (rs.getString("state").equals("未选")) {
                return null;
            }
            else {
                return rs.getString("instructor");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
