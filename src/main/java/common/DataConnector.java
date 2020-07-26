package common;

import java.sql.*;

public class DataConnector {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://mysql:3306/MoniterData?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8";//URL指向访问的数据库名，jsp_data
    private static final String user = "MoniterData";//Mysql配置时的用户名
    private static final String password = "MoniterData";//密码
    private Connection conn;

    public void connect(){
        try {
            Class.forName(driver);//加载驱动程序
            conn = DriverManager.getConnection(url, user, password);//链接数据库
        } catch (ClassNotFoundException e) {
            System.out.println("No Drive!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("No Connection!");
            e.printStackTrace();
        }
    }

    public ResultSet query(String SQL) {
        try {
            connect();
            return conn.createStatement().executeQuery(SQL);
        } catch (SQLException e) {
            System.out.println("Error SQL:" + SQL);
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            conn.close();
            conn = null;
        } catch (SQLException e) {
            System.out.println("Close Failed!");
            e.printStackTrace();
        }
    }
}
