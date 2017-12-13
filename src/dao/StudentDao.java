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
    public ObservableList<Student> findStudentsByState (String state) {
        ObservableList<Student> students = FXCollections.observableArrayList();
        // 获得数据库连接
        Connection conn= DBHelper.getConnection();

        try {
            // 预编译sql语句
            String sql="select * from student where state=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, state);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("id"));
                student.setClassName(rs.getString("class"));
                student.setInstructor(rs.getString("instructor"));
                student.setMajor(rs.getString("major"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setState(rs.getString("state"));
                student.setTelephone(rs.getString("telephone"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
