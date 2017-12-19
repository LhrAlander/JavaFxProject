package dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Alander on 2017/12/14.
 */
public class UserDao {


    // 获得数据库连接
    private Connection conn;

    private PreparedStatement loginSql;

    private PreparedStatement stIdSql;
    private PreparedStatement stNameSql;
    private PreparedStatement stIdNameSql;
    private PreparedStatement userEditSql;
    private PreparedStatement teacherEditSql;
    private PreparedStatement studentEditSql;
    private PreparedStatement resetPWDSql;
    private PreparedStatement studentByTeacherSql;
    private PreparedStatement studentByTeacherAndStateSql;


    public UserDao() {
        String sql="select * from user where userId=? and userPassword=?";
        conn= DBHelper.getConnection();
        try {
            loginSql = conn.prepareStatement(sql);
            stIdSql = conn.prepareStatement("select * from user where userIdentity = ? and userId LIKE ?");
            stNameSql = conn.prepareStatement("select * from user where userIdentity = ? and userName LIKE ?");
            stIdNameSql = conn.prepareStatement("select * from user where userIdentity = ? and userId like ? AND userName LIKE ?");
            userEditSql = conn.prepareStatement("update user set userName = ?, telephone = ? where userId = ?");
            teacherEditSql = conn.prepareStatement("update teacher set name = ? where userId = ?");
            studentEditSql = conn.prepareStatement("update student set name = ? where userId = ?");
            resetPWDSql = conn.prepareStatement("update user set userPassword = '1234567' where userId = ?");
            studentByTeacherSql = conn.prepareStatement(" select distinct user.userId, user.userName, userSex, user.telephone, student.state from user, student, teacher where user.userId = student.userId and student.instructor = ?;");
            studentByTeacherAndStateSql = conn.prepareStatement(" select distinct user.userId, user.userName, userSex, user.telephone, student.state from user, student, teacher where user.userId = student.userId and student.instructor = ? and student.state = ?;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 登录功能
    public HashMap<String, Object> login(String userId, String pwd) {
        HashMap<String, Object> returnData = new HashMap<>();
        User user = null;
        try {
            loginSql.setString(1, userId);
            loginSql.setString(2, pwd);
            ResultSet loginRS = loginSql.executeQuery();
            while (loginRS.next()) {
                user = new User();
                user.setTelephone(loginRS.getString("telephone"));
                user.setUserId(loginRS.getString("userId"));
                user.setUserIdentity(loginRS.getString("userIdentity"));
                user.setUserName(loginRS.getString("userName"));
                user.setUserPassword(loginRS.getString("userPassword"));
                user.setUserSex(loginRS.getString("userSex"));
                returnData.put("code", "200");
                returnData.put("user", user);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user == null) {
            returnData.put("code", "404");
        }

        return returnData;

    }

    // 获取所有的学生用户信息
    public ObservableList<User> getAllStudents () {
        ObservableList<User> students = FXCollections.observableArrayList();
        try {
            ResultSet rs = conn.createStatement().executeQuery("select * from user where userIdentity = '学生'");
            while (rs.next()) {
                students.add(getUserFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // 获取所有的导师用户信息
    public ObservableList<User> getAllTeachers () {
        ObservableList<User> teachers = FXCollections.observableArrayList();
        try {
            ResultSet rs = conn.createStatement().executeQuery("select * from user where userIdentity = '教师'");
            while (rs.next()) {
                teachers.add(getUserFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    // 根据用户id和用户名搜索用户
    public ObservableList<User> getStudentsByInfo (String infoType, String userId, String userName) {
        ResultSet rs = null;
        ObservableList<User> students = FXCollections.observableArrayList();
        System.out.println(infoType);
        try {
            if (userId != null && !userId.equals("")) {
                // 模糊查询
                if (userName == null || userName.equals("")) {
                    stIdSql.setString(1, infoType);
                    stIdSql.setString(2, "%" + userId + "%");
                    rs = stIdSql.executeQuery();
                }
                else {
                    stIdNameSql.setString(1, infoType);
                    stIdNameSql.setString(2, "%" + userId + "%");
                    stIdNameSql.setString(3, "%" + userName + "%");
                    rs = stIdNameSql.executeQuery();
                }
            }
            else {
                if (userName != null && !userName.equals("")) {
                    stNameSql.setString(1, infoType);
                    stNameSql.setString(2, "%" + userName + "%");
                    rs = stNameSql.executeQuery();
                }
                else {
                    if (infoType.equals("学生"))
                        return getAllStudents();
                    return getAllTeachers();
                }
            }
            while (rs != null && rs.next()) {
                students.add(getUserFromRS(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // 修改用户基本信息
    public Boolean changeUserInfo (String userId, String userName, String telephone) {
        try {
            userEditSql.setString(1, userName);
            userEditSql.setString(2, telephone);
            userEditSql.setString(3, userId);
            teacherEditSql.setString(1, userName);
            teacherEditSql.setString(2, userId);
            studentEditSql.setString(1, userName);
            studentEditSql.setString(2, userId);
            userEditSql.executeUpdate();
            teacherEditSql.executeUpdate();
            studentEditSql.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 重置用户密码
    public Boolean resetUserPWD (String userId) {
        try {
            resetPWDSql.setString(1, userId);
            resetPWDSql.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private User getUserFromRS(ResultSet rs) {
        User user = new User();
        try {
            user.setUserSex(rs.getString("userSex"));
            user.setUserPassword(rs.getString("userPassword"));
            user.setUserName(rs.getString("userName"));
            user.setUserId(rs.getString("userId"));
            user.setTelephone(rs.getString("telephone"));
            user.setUserIdentity(rs.getString("userIdentity"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public ObservableList<User> getStudentByInstructor (String teacherId) {
        ObservableList<User> students = FXCollections.observableArrayList();
        try {
            studentByTeacherSql.setString(1, teacherId);
            ResultSet rs = studentByTeacherSql.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setTelephone(rs.getString("telephone"));
                user.setUserSex(rs.getString("userSex"));
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setState(rs.getString("state"));
                user.setUserIdentity("学生");
                students.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public ObservableList<User> getStudentByInstructorAndState (String teacherId, String state) {
        ObservableList<User> students = FXCollections.observableArrayList();
        try {
            studentByTeacherAndStateSql.setString(1, teacherId);
            studentByTeacherAndStateSql.setString(2, state);
            ResultSet rs = studentByTeacherAndStateSql.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setTelephone(rs.getString("telephone"));
                user.setUserSex(rs.getString("userSex"));
                user.setUserId(rs.getString("userId"));
                user.setUserName(rs.getString("userName"));
                user.setState(rs.getString("state"));
                user.setUserIdentity("学生");
                students.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

}
