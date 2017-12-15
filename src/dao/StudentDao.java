package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Student;
import util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDao {



    private Connection conn;
    private  PreparedStatement stateQuery;

    public StudentDao () {
        conn= DBHelper.getConnection();
        try {
            stateQuery = conn.prepareStatement("select * from student where state=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Student> findStudentsByState (String state) {
        ObservableList<Student> students = FXCollections.observableArrayList();

        try {
            // 预编译sql语句
            String sql="select * from student where state=?";
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
}
