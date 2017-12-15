package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    //数据库的URL
    private static final String URL="jdbc:mysql://127.0.0.1:3306/pair-cms?useUnicode=true&characterEncoding=utf8";
    //数据库连接的用户名
    private static final String USER="root";
    //密码
    private static final String PASSWORD="root";
    private static Connection conn=null;
    static {
        try {
            //加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //获得数据库的连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
        } catch (SQLException e) {
            System.out.println("Sorry,can`t find the MySql!");
        }
    }

    public static Connection getConnection(){
        return conn;
    }

}
