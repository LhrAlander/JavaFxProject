package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Teacher;
import util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alander on 2017/12/15.
 */
public class TeacherDao {

    private Connection conn;

    private PreparedStatement teacherIdSql;
    private PreparedStatement teacherNameSql;
    private PreparedStatement teacherIdNameSql;
    private PreparedStatement studentNumberUpdate;
    private PreparedStatement stCountSql;
    private PreparedStatement phoneCountSql;

    public TeacherDao () {
        conn = DBHelper.getConnection();
        try {
            teacherIdSql = conn.prepareStatement("select * from teacher where userId LIKE ?;");
            teacherNameSql = conn.prepareStatement("SELECT * from teacher where name like ?;");
            teacherIdNameSql = conn.prepareStatement("select * from teacher where userId like ? and name LIKE ?;");
            studentNumberUpdate = conn.prepareStatement("update teacher set studentNumber = ?;");
            stCountSql = conn.prepareStatement("select count(*) from student where instructor = ?;");
            phoneCountSql = conn.prepareStatement("select telephone from user where userId = ?;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Teacher> getAllTeachers () {
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM teacher");
            while (rs.next()) {
                teachers.add(getTeacherFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }

    public ObservableList<Teacher> getTeachersByInfo (String userId, String userName) {
        ResultSet rs = null;
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        try {
            if (userId != null && !userId.equals("")) {
                // 模糊查询
                if (userName == null || userName.equals("")) {
                    teacherIdSql.setString(1, "%" + userId + "%");
                    rs = teacherIdSql.executeQuery();
                }
                else {
                    teacherIdNameSql.setString(1, "%" + userId + "%");
                    teacherIdNameSql.setString(2, "%" + userName + "%");
                    rs = teacherIdNameSql.executeQuery();
                }
            }
            else {
                if (userName != null && !userName.equals("")) {
                    teacherNameSql.setString(1, "%" + userName + "%");
                    rs = teacherNameSql.executeQuery();
                }
                else {
                    return getAllTeachers();
                }
            }
            while (rs != null && rs.next()) {
                teachers.add(getTeacherFromRS(rs));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }

    public Boolean changeStudentNumber (int stNum) {
        try {
            studentNumberUpdate.setString(1, stNum+"");
            studentNumberUpdate.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Teacher getTeacherFromRS (ResultSet rs) {
        Teacher teacher = new Teacher();
        try {
            teacher.setName(rs.getString("name"));
            teacher.setStudentNumber(rs.getInt("studentNumber"));
            teacher.setTeacherMajor(rs.getString("teacherMajor"));
            teacher.setTeacherTitle(rs.getString("teacherTitle"));
            teacher.setUserId(rs.getString("userId"));
            teacher.setCanBeSelected(judgeTeacherSelect(teacher));
            teacher.setTelephone(getTeacherTelephone(teacher));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacher;
    }

    private String judgeTeacherSelect (Teacher teacher) {
        int stNum = teacher.getStudentNumber();
        String teacherId = teacher.getUserId();
        try {
            stCountSql.setString(1, teacherId);
            ResultSet rs = stCountSql.executeQuery();
            rs.next();
           int currentNum = rs.getInt(1);
           if (stNum > currentNum) {
               return "是";
           }
           return "否";
        } catch (SQLException e) {
            e.printStackTrace();
            return "否";
        }
    }

    private String getTeacherTelephone (Teacher teacher) {
        try {
            phoneCountSql.setString(1, teacher.getUserId());
            ResultSet rs = phoneCountSql.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return  "";
        }
    }

}
