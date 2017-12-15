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


    public UserDao() {
        String sql="select * from user where userId=? and userPassword=?";
        conn= DBHelper.getConnection();
        try {
            loginSql = conn.prepareStatement(sql);
            stIdSql = conn.prepareStatement("select * from user where userIdentity = '学生' and userId LIKE ?");
            stNameSql = conn.prepareStatement("select * from user where userIdentity = '学生' and userName LIKE ?");
            stIdNameSql = conn.prepareStatement("select * from user where userIdentity = '学生' and userId like ? AND userName LIKE ?");
            userEditSql = conn.prepareStatement("update user set userName = ?, telephone = ? where userId = ?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public ObservableList<User> getStudentsByInfo (String userId, String userName) {
        ResultSet rs = null;
        ObservableList<User> students = FXCollections.observableArrayList();
        try {
            if (userId != null && !userId.equals("")) {
                // 模糊查询
                if (userName == null || userName.equals("")) {
                    stIdSql.setString(1, "%" + userId + "%");
                    rs = stIdSql.executeQuery();
                }
                else {
                    stIdNameSql.setString(1, "%" + userId + "%");
                    stIdNameSql.setString(2, "%" + userName + "%");
                    rs = stIdNameSql.executeQuery();
                }
            }
            else {
                if (userName != null && !userName.equals("")) {
                    stNameSql.setString(1, "%" + userName + "%");
                    rs = stNameSql.executeQuery();
                }
                else {
                    return getAllStudents();
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

    public Boolean changeUserInfo (String userId, String userName, String telephone) {
        try {
            userEditSql.setString(1, userName);
            userEditSql.setString(2, telephone);
            userEditSql.setString(3, userId);
            userEditSql.executeUpdate();
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

}
